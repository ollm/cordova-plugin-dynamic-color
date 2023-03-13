var exec = require('cordova/exec'),
	cordova = require('cordova');

var PLUGIN_NAME = 'DynamicColor';

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