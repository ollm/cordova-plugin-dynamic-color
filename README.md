# cordova-plugin-dynamic-color

> The `DynamicColor` object provides some functions to obtain current [dynamic color](https://m3.material.io/styles/color/dynamic-color/overview) palette/colors (Android 12+) and dark theme status (Android 9+).

**Table of Contents**

- [Installation](#installation)
- [Methods](#methods)
  - [Simple example of plugin usage](#simple-example-of-plugin-usage)
  - [DynamicColor.isDynamicColorAvailable](#dynamiccolorisdynamiccoloravailable)
  - [DynamicColor.dayNight](#dynamiccolordaynight)
  - [DynamicColor.colors](#dynamiccolorcolors)
  - [DynamicColor.palette](#dynamiccolorpalette)
  - [DynamicColor.tintColors](#dynamiccolortintcolors)
  - [DynamicColor.tintSurfaceColors](#dynamiccolortintsurfacecolors)
  - [DynamicColor.mixColor](#dynamiccolormixcolor)
  - [DynamicColor.mixColorElevation](#dynamiccolormixcolorelevation)
  - [DynamicColor.colorsToCssVars](#dynamiccolorcolorstocssvars)
  - [DynamicColor.paletteToCssVars](#dynamiccolorpalettetocssvars)
  - [DynamicColor.colorsToDom](#dynamiccolorcolorstodom)
  - [DynamicColor.paletteToDom](#dynamiccolorpalettetodom)
- [Events](#events)
  - [dynamicColorChange](#dynamiccolorchange)


## Installation

`cordova plugin add cordova-plugin-dynamic-color`

## Methods

This plugin defines global `DynamicColor` object.

Although in the global scope, it is not available until after the `deviceready` event.

```js
document.addEventListener("deviceready", onDeviceReady, false);

function onDeviceReady()
{
    console.log(DynamicColor);
}
```

### Simple example of plugin usage

```js
document.addEventListener("deviceready", onDeviceReady, false);

function onDeviceReady()
{
	DynamicColor.isDynamicColorAvailable(function(available) {

		if(available) {

			getDynamicColor();

			document.addEventListener('dynamicColorChange', getDynamicColor);
		}

	});

}

function getDynamicColor() {

	DynamicColor.colors(function(colors) {

		DynamicColor.colorsToDom(DynamicColor.tintSurfaceColors(colors.dayNight));

	});

	// Optional palette colors
	DynamicColor.palette(function(colors) {

		DynamicColor.paletteToDom(colors.palette);

	});

}
```

```css
.myAppStyle
{
	color: var(--md-sys-color-primary);
	background: var(--md-ref-palette-neutral-variant95);
}
```

### DynamicColor.isDynamicColorAvailable

Check if the DynamicColor its available in current Android device.

```js
DynamicColor.isDynamicColorAvailable(function(available) {

	if(available) {

	}

});
```

### DynamicColor.dayNight

Check current DayNight theme (check if dark theme is on or off).

```js
DynamicColor.dayNight(function(theme) {

	if(theme == 'light') {

	} else { // theme == 'dark'

	}

});
```

### DynamicColor.colors

Return the DynamicColor colors

```js
DynamicColor.colors(function(colors) {

	colors = {
		theme: 'light', // 'light' or 'dark'
		light: {
			primary: '#8D4E2A',
			onPrimary: '#FFFFFF',
			primaryContainer: '#331100',
			onPrimaryContainer: '#000000',
			secondary: '#765848',
			onSecondary: '#FFFFFF',
			secondaryContainer: '#FFDBCA',
			onSecondaryContainer: '#2B160A',
			tertiary: '#646032',
			onTertiary: '#FFFFFF',
			tertiaryContainer: '#1E1C00',
			onTertiaryContainer: '#000000',
			error: '#B3261E',
			onError: '#FFFFFF',
			errorContainer: '#F9DEDC',
			onErrorContainer: '#410E0B',
			outline: '#84736A',
			background: '#FFFBFF',
			onBackground: '#201A17',
			surface: '#FFFBFF',
			onSurface: '#201A17',
			surfaceVariant: '#F4DED4',
			onSurfaceVariant: '#52443D',
			inverseSurface: '#362F2C',
			inverseOnSurface: '#FBEEE9',
			inversePrimary: '#FFB68F',
			shadow: '#000000',
			surfaceTint: '#000000',
			outlineVariant: '#D7C2B9',
			scrim: '#000000',
		},
		dark: {
			...
		}
		dayNight: {
			...
		}
	}

});
```

### DynamicColor.palette

Return the DynamicColor palette

```js
DynamicColor.palette(function(colors) {

	colors = {
		theme: 'light', // 'light' or 'dark'
		palette: {
			error0: '#000000',
			error10: '#410E0B',
			error20: '#601410',
			error30: '#8C1D18',
			error40: '#B3261E',
			error50: '#DC362E',
			error60: '#E46962',
			error70: '#EC928E',
			error80: '#F2B8B5',
			error90: '#F9DEDC',
			error95: '#FCEEEE',
			error99: '#FFFBF9',
			error100: '#FFFFFF',
			neutral0: '#000000',
			neutral10: '#201A17',
			neutral20: '#362F2C',
			neutral30: '#4D4542',
			neutral40: '#655D59',
			neutral50: '#7D7470',
			neutral60: '#998F8A',
			neutral70: '#B4A9A4',
			neutral80: '#D0C4BF',
			neutral90: '#ECE0DB',
			neutral95: '#FBEEE9',
			neutral99: '#FFFBFF',
			neutral100: '#FFFFFF',
			neutralVariant0: '#000000',
			neutralVariant10: '#241913',
			neutralVariant20: '#3B2E27',
			neutralVariant30: '#52443D',
			neutralVariant40: '#6B5B53',
			neutralVariant50: '#84736A',
			neutralVariant60: '#9F8D84',
			neutralVariant70: '#BBA79E',
			neutralVariant80: '#D7C2B9',
			neutralVariant90: '#F4DED4',
			neutralVariant95: '#FFEDE6',
			neutralVariant99: '#FFFBFF',
			neutralVariant100: '#FFFFFF',
			primary0: '#000000',
			primary10: '#331100',
			primary20: '#532201',
			primary30: '#703715',
			primary40: '#8D4E2A',
			primary50: '#A9653F',
			primary60: '#C87F56',
			primary70: '#E7996E',
			primary80: '#FFB68F',
			primary90: '#FFDBCA',
			primary95: '#FFEDE6',
			primary99: '#FFFBFF',
			primary100: '#FFFFFF',
			secondary0: '#000000',
			secondary10: '#2B160A',
			secondary20: '#432B1D',
			secondary30: '#5C4132',
			secondary40: '#765848',
			secondary50: '#906F5E',
			secondary60: '#AC8978',
			secondary70: '#C9A391',
			secondary80: '#E6BEAB',
			secondary90: '#FFDBCA',
			secondary95: '#FFEDE6',
			secondary99: '#FFFBFF',
			secondary100: '#FFFFFF',
			tertiary0: '#000000',
			tertiary10: '#1E1C00',
			tertiary20: '#343108',
			tertiary30: '#4B481D',
			tertiary40: '#646032',
			tertiary50: '#7C7847',
			tertiary60: '#97925F',
			tertiary70: '#B2AD78',
			tertiary80: '#CEC891',
			tertiary90: '#EBE4AA',
			tertiary95: '#F9F3B8',
			tertiary99: '#FFFBFF',
			tertiary100: '#FFFFFF'
		}
	}

});
```

### DynamicColor.tintColors

Apply the tint according to [Material Design Guidelines](https://m3.material.io/styles/color/the-color-system/color-roles#c0cdc1ba-7e67-4d6a-b294-218f659ff648), you can also use css [`color-mix`](https://developer.chrome.com/blog/css-color-mix/) to mix the surface with tint (`color-mix(in srgb, var(--md-sys-color-on-surface), var(--md-sys-color-on-surface-tint) 5%)`), but currently it's not [supported](https://caniuse.com/?search=color-mix) for many browsers.

```js
var colors = DynamicColor.tintColors(colors.light, Array onlyKeys = false);

colors = {
	surface: '#FFFBFF',
	surface1: '#f9f2f4',
	surface2: '#f6edee',
	surface3: '#f2e8e8',
	surface4: '#f1e6e5',
	surface5: '#efe3e1',
	...
}

/*
--md-sys-color-surface: #FFFBFF;
--md-sys-color-surface1: #f9f2f4;
--md-sys-color-surface2: #f6edee;
--md-sys-color-surface3: #f2e8e8;
--md-sys-color-surface4: #f1e6e5;
--md-sys-color-surface5: #efe3e1;
...
*/

```

- __onlyKeys__ Keys to generate tint versions, by default all keys.

### DynamicColor.tintSurfaceColors

Same as above code, but only applies to surfaces (Including background).

```js
var colors = DynamicColor.tintSurfaceColors(colors.light);

colors = {
	surface: '#FFFBFF',
	surface1: '#f9f2f4',
	surface2: '#f6edee',
	surface3: '#f2e8e8',
	surface4: '#f1e6e5',
	surface5: '#efe3e1',
	...
}

/*
--md-sys-color-surface: #FFFBFF;
--md-sys-color-surface1: #f9f2f4;
--md-sys-color-surface2: #f6edee;
--md-sys-color-surface3: #f2e8e8;
--md-sys-color-surface4: #f1e6e5;
--md-sys-color-surface5: #efe3e1;
...
*/
```

### DynamicColor.mixColor

Mix two colors to one acording a amount.

```js
DynamicColor.mixColor(String color1, String color2, Float amount);
```

-  __amount__ Amount, from 0.0 to 1.0

### DynamicColor.mixColorElevation

Mix two colors to one acording a [Material Design Guidelines Elevation](https://m3.material.io/styles/color/the-color-system/color-roles#de5b0f5e-c4a5-48ef-8670-369682e9c7b7).

```js
DynamicColor.mixColorElevation(String color1, String color2, Int elevation);
```

-  __elevation__ Elevation, from 1 to 5.

### DynamicColor.colorsToCssVars

Convert the colors object to css vars.

```js
DynamicColor.colors(function(colors) {

	var cssVars = DynamicColor.colorsToCssVars(colors.light, String prefix = '--md-sys-color');

	/*
	--md-sys-color-primary: #8D4E2A;
	--md-sys-color-on-primary: #FFFFFF;
	...
	*/

}
```

### DynamicColor.paletteToCssVars

Convert the palette object to css vars.

```js
DynamicColor.palette(function(colors) {

	var cssVars = DynamicColor.paletteToCssVars(colors.palette, String prefix = '--md-ref-palette');

	/*
	--md-ref-palette-error0: #000000;
	--md-ref-palette-error10: #410E0B;
	...
	*/

}
```

### DynamicColor.colorsToDom

Convert the colors object to css vars and insert/update it in the dom.

```js
DynamicColor.colors(function(colors) {

	DynamicColor.colorsToDom(colors.dark, String prefix = '--md-sys-color');

}
```

### DynamicColor.paletteToDom

Convert the palette object to css vars and insert/update it in the dom.

```js
DynamicColor.palette(function(colors) {

	DynamicColor.paletteToDom(colors.palette, String prefix = '--md-ref-palette');

}
```

## Events

### dynamicColorChange

This event is fired when any change in DayNight or DynamiColor (or both at the same time) is detected, this event only checks if there have been any changes when the app goes from the background to the foreground, if the DayNight/DynamiColor is changed while the app is in the foreground it will not be detected.

```js
document.addEventListener('dynamicColorChange', function(event) {

	event.changed = {
		dayNight: true, // true if the DayNight theme have changed (dark theme turned on or off)
		dynamicColor: true, // true if the DynamiColor colors/palette have changed
	}

});
```