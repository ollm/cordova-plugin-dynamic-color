var exec = require('cordova/exec'),
	cordova = require('cordova');

var PLUGIN_NAME = 'DynamicColor';

function hexToRgb(hex) {

	var result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
	return result ? {
		r: parseInt(result[1], 16),
		g: parseInt(result[2], 16),
		b: parseInt(result[3], 16)
	} : null;

}

function componentToHex(c) {

	var hex = c.toString(16);
	return hex.length == 1 ? '0' + hex : hex;

}

function rgbToHex(r, g, b) {

	return '#' + componentToHex(r) + componentToHex(g) + componentToHex(b);

}

// https://m3.material.io/styles/color/the-color-system/color-roles#c0cdc1ba-7e67-4d6a-b294-218f659ff648
var tintElevations = {
	1: 0.05,
	2: 0.08,
	3: 0.11,
	4: 0.12,
	5: 0.14,
};

function _mixColor(rgb1, rgb2, amount) {

	var r = Math.round(rgb1.r + (rgb2.r - rgb1.r) * amount),
		g = Math.round(rgb1.g + (rgb2.g - rgb1.g) * amount),
		b = Math.round(rgb1.b + (rgb2.b - rgb1.b) * amount);

	return rgbToHex(r, g, b);

}

function mixColor(color1, color2, amount) {

	return _mixColor(hexToRgb(color1), hexToRgb(color2), amount);

}

function _mixColorElevation(rgb1, rgb2, elevation) {

	var amount = tintElevations[elevation];

	return _mixColor(rgb1, rgb2, amount);

}

function mixColorElevation(color1, color2, elevation) {

	return _mixColorElevation(hexToRgb(color1), hexToRgb(color2), elevation);

}

function tintColors(colors, onlyKeys = false) {

	if(onlyKeys)
		onlyKeys = onlyKeys.reduce(function(acc, curr){acc[curr] = true; return acc}, {});

	var tint = colors.surfaceTint,
		rgbTint = hexToRgb(tint);

	for(var key in colors)
	{
		if(key != 'surfaceTint' && (onlyKeys === false || onlyKeys[key]))
		{
			for(var i = 1; i <= 5; i++)
			{
				colors[key+i] = _mixColorElevation(hexToRgb(colors[key]), rgbTint, i);
			}
		}
	}

	return colors;
}

function tintSurfaceColors(colors) {

	return tintColors(colors, [
		'background',
		'onBackground',
		'surface',
		'onSurface',
		'surfaceVariant',
		'onSurfaceVariant',
		'inverseSurface',
		'inverseOnSurface',
	]);

}

function colorsToCssVars(colors, prefix = '--md-sys-color') {

	var cssVars = '';

	for(var key in colors)
	{
		cssVars += prefix+'-'+key.replace(/([A-Z])/g, '-$1').toLowerCase()+': '+colors[key]+';\n';
	}

	return cssVars;
}

function paletteToCssVars(colors, prefix = '--md-ref-palette') {

	return colorsToCssVars(colors, prefix);

}

function _colorsToDom(colors, prefix, className) {

	var cssVars = colorsToCssVars(colors, prefix);
	cssVars = ':root {\n'+cssVars+'}';

	var element = document.querySelector('.'+className);

	if(element) {
		element.innerHTML = cssVars;
	} else {
		var head = document.head || document.getElementsByTagName('head')[0],
			style = document.createElement('style');
		style.className = className;
		style.type = 'text/css';
		style.appendChild(document.createTextNode(cssVars));
		head.appendChild(style);
	}

	return true;
}

function colorsToDom(colors, prefix = '--md-sys-color') {

	_colorsToDom(colors, prefix, 'dynamicColorCssVarsColors');

	return true;
}


function paletteToDom(colors, prefix = '--md-ref-palette') {

	_colorsToDom(colors, prefix, 'dynamicColorCssVarsPalette');

	return true;
}

function dayNight(cb) {

	exec(function(dayNightIsDay) {
		cb(dayNightIsDay ? 'light' : 'dark');
	}, null, PLUGIN_NAME, 'dayNightIsDay', []);

}

var currentDayNightIsDay = null;
var currentMainColors = null;

function checkChanges() {

	exec(function(dayNightIsDay) {

		exec(function(mainColors) {

			if(currentDayNightIsDay === null || currentMainColors === null) { // First check
				currentDayNightIsDay = dayNightIsDay;
				currentMainColors = mainColors;
				return;
			}

			var dayNightIsDayChanged = (dayNightIsDay !== currentDayNightIsDay);
			var mainColorsChanged = (mainColors.primary !== currentMainColors.primary || mainColors.secondary !== currentMainColors.secondary || mainColors.tertiary !== currentMainColors.tertiary);

			if(dayNightIsDayChanged || mainColorsChanged)
			{
				currentDayNightIsDay = dayNightIsDay;
				currentMainColors = mainColors;

				cordova.fireDocumentEvent('dynamicColorChange', {
					changed: {
						dayNight: dayNightIsDayChanged,
						dynamicColor: mainColorsChanged,
					}
				});
			}

		}, null, PLUGIN_NAME, 'mainColors', []);

	}, null, PLUGIN_NAME, 'dayNightIsDay', []);

}

var DynamicColor = {
	isDynamicColorAvailable: function(cb) {
		exec(cb, null, PLUGIN_NAME, 'isDynamicColorAvailable', []);
	},
	colors: function(cb) {
		exec(cb, null, PLUGIN_NAME, 'colors', []);
	},
	palette: function(cb) {
		exec(cb, null, PLUGIN_NAME, 'palette', []);
	},
	tintColors: tintColors,
	tintSurfaceColors: tintSurfaceColors,
	mixColor: mixColor,
	mixColorElevation: mixColorElevation,
	colorsToCssVars: colorsToCssVars,
	paletteToCssVars: paletteToCssVars,
	colorsToDom: colorsToDom,
	paletteToDom: paletteToDom,
	dayNight: dayNight,
};

document.addEventListener('resume', checkChanges);

// prime it. setTimeout so that proxy gets time to init
window.setTimeout(function() {
    checkChanges();
}, 0);

module.exports = DynamicColor;