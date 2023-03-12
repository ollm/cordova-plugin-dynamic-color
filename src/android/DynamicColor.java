package com.ollm.cordova.plugin.dynamiccolor;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.content.res.Resources;
import android.os.Build;
import android.util.Log;
import android.util.TypedValue;

import androidx.core.content.ContextCompat;

import com.google.android.material.R;
import com.google.android.material.color.DynamicColors;

public class DynamicColor extends CordovaPlugin {
	private static final String TAG = "DynamicColor";

	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		super.initialize(cordova, webView);
		Log.d(TAG, "Initializing DynamicColor");
	}

	public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
		switch (action) {
			case "isDynamicColorAvailable":
				isDynamicColorAvailable(callbackContext);
				break;
			case "colors":
				getDynamicColor(callbackContext);
				break;
			case "palette":
				getDynamicPalette(callbackContext);
				break;
			case "dayNightIsDay":
				Application app = this.cordova.getActivity().getApplication();
				callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, dayNightIsDay(app, app.getApplicationContext())));
				break;
			case "mainColors":
				getDynamicMainColor(callbackContext);
				break;
		}
		return true;
	}

	private String intColorToHex(int color) {
		return String.format("#%06X", (0xFFFFFF & color));
	}

	private boolean dayNightIsDay(Application app, Context context) {

		if(Build.VERSION.SDK_INT >= 28) {

			Resources resources = app.getResources();
			int nightModeFlags = resources.getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

			if(nightModeFlags != Configuration.UI_MODE_NIGHT_YES) {
				return true;
			} else {
				return false;
			}

		} else {
			return true;
		}

		/*
		String packageName = app.getPackageName();

		Context dynamicColorContextLight = DynamicColors.wrapContextIfAvailable(context, R.style.Theme_Material3_DynamicColors_Light);
		Context dynamicColorContextDayNight = DynamicColors.wrapContextIfAvailable(context, R.style.Theme_Material3_DynamicColors_DayNight);

		int[] attrsToResolve = {
			R.attr.colorPrimary,
			R.attr.colorSecondary,
			R.attr.colorTertiary,
		};

		TypedArray taLight = dynamicColorContextLight.obtainStyledAttributes(attrsToResolve);
		TypedArray taDayNight = dynamicColorContextDayNight.obtainStyledAttributes(attrsToResolve);

		if(taLight.getColor(0, 0) == taDayNight.getColor(0, 0) && taLight.getColor(1, 0) == taDayNight.getColor(1, 0) && taLight.getColor(2, 0) == taDayNight.getColor(2, 0)) {
			return true;
		} else {
			return false;
		}
		*/
	}

	private boolean isDynamicColorAvailable(final CallbackContext callbackContext) {

		if (Build.VERSION.SDK_INT >= 32 && DynamicColors.isDynamicColorAvailable()) {
			callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, true));
		} else {
			callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, false));
		}

		return true;
	}

	private boolean getDynamicColor(final CallbackContext callbackContext) {

		Log.d(TAG, "Start get DynamicColor");

		JSONObject colors = new JSONObject();

		Application app = this.cordova.getActivity().getApplication();
		Context context = app.getApplicationContext();

		if (Build.VERSION.SDK_INT >= 32 && DynamicColors.isDynamicColorAvailable()) {

				JSONObject colorsLight = new JSONObject();
				JSONObject colorsDark = new JSONObject();
				JSONObject colorsDayNight = new JSONObject();

				try {
					// Light
					colorsLight.put("primary", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_light_primary)));
					colorsLight.put("onPrimary", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_light_on_primary)));
					colorsLight.put("primaryContainer", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_light_primary_container)));
					colorsLight.put("onPrimaryContainer", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_light_on_primary_container)));
					colorsLight.put("secondary", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_light_secondary)));
					colorsLight.put("onSecondary", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_light_on_secondary)));
					colorsLight.put("secondaryContainer", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_light_secondary_container)));
					colorsLight.put("onSecondaryContainer", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_light_on_secondary_container)));
					colorsLight.put("tertiary", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_light_tertiary)));
					colorsLight.put("onTertiary", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_light_on_tertiary)));
					colorsLight.put("tertiaryContainer", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_light_tertiary_container)));
					colorsLight.put("onTertiaryContainer", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_light_on_tertiary_container)));
					colorsLight.put("error", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_light_error)));
					colorsLight.put("onError", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_light_on_error)));
					colorsLight.put("errorContainer", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_light_error_container)));
					colorsLight.put("onErrorContainer", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_light_on_error_container)));
					colorsLight.put("outline", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_light_outline)));
					colorsLight.put("background", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_light_background)));
					colorsLight.put("onBackground", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_light_on_background)));
					colorsLight.put("surface", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_light_surface)));
					colorsLight.put("onSurface", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_light_on_surface)));
					colorsLight.put("surfaceVariant", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_light_surface_variant)));
					colorsLight.put("onSurfaceVariant", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_light_on_surface_variant)));
					colorsLight.put("inverseSurface", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_light_inverse_surface)));
					colorsLight.put("inverseOnSurface", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_light_inverse_on_surface)));
					colorsLight.put("inversePrimary", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_light_inverse_primary)));
					colorsLight.put("shadow", "#000000");
					colorsLight.put("surfaceTint", "#000000"); //intColorToHex(ContextCompat.getColor(context, R.color.m3_assist_chip_icon_tint_color)));
					colorsLight.put("outlineVariant", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_light_outline_variant)));
					colorsLight.put("scrim", intColorToHex(ContextCompat.getColor(context, R.color.mtrl_scrim_color)));

					// Dark
					colorsDark.put("primary", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_dark_primary)));
					colorsDark.put("onPrimary", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_dark_on_primary)));
					colorsDark.put("primaryContainer", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_dark_primary_container)));
					colorsDark.put("onPrimaryContainer", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_dark_on_primary_container)));
					colorsDark.put("secondary", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_dark_secondary)));
					colorsDark.put("onSecondary", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_dark_on_secondary)));
					colorsDark.put("secondaryContainer", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_dark_secondary_container)));
					colorsDark.put("onSecondaryContainer", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_dark_on_secondary_container)));
					colorsDark.put("tertiary", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_dark_tertiary)));
					colorsDark.put("onTertiary", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_dark_on_tertiary)));
					colorsDark.put("tertiaryContainer", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_dark_tertiary_container)));
					colorsDark.put("onTertiaryContainer", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_dark_on_tertiary_container)));
					colorsDark.put("error", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dark_error)));
					colorsDark.put("onError", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dark_on_error)));
					colorsDark.put("errorContainer", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dark_error_container)));
					colorsDark.put("onErrorContainer", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dark_on_error_container)));
					colorsDark.put("outline", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_dark_outline)));
					colorsDark.put("background", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_dark_background)));
					colorsDark.put("onBackground", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_dark_on_background)));
					colorsDark.put("surface", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_dark_surface)));
					colorsDark.put("onSurface", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_dark_on_surface)));
					colorsDark.put("surfaceVariant", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_dark_surface_variant)));
					colorsDark.put("onSurfaceVariant", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_dark_on_surface_variant)));
					colorsDark.put("inverseSurface", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_dark_inverse_surface)));
					colorsDark.put("inverseOnSurface", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_dark_inverse_on_surface)));
					colorsDark.put("inversePrimary", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_dark_inverse_primary)));
					colorsDark.put("shadow", "#000000");
					colorsDark.put("surfaceTint", "#000000"); //intColorToHex(ContextCompat.getColor(context, R.color.m3_assist_chip_icon_tint_color)));
					colorsDark.put("outlineVariant", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_dark_outline_variant)));
					colorsDark.put("scrim", intColorToHex(ContextCompat.getColor(context, R.color.mtrl_scrim_color)));

					// DayNight
					boolean dayNightIsDay = dayNightIsDay(app, context);

					if(dayNightIsDay) {
						colorsDayNight = colorsLight;
					} else {
						colorsDayNight = colorsDark;
					}

					colors.put("light", colorsLight);
					colors.put("dark", colorsDark);
					colors.put("dayNight", colorsDayNight);
					colors.put("theme", dayNightIsDay ? "light" : "dark");
				} catch (JSONException e) {
					callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.JSON_EXCEPTION));
					return true;
				}
		}

		Log.d(TAG, "End get DynamicColor");

		callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, colors));

		return true;

	}

	private boolean getDynamicPalette(final CallbackContext callbackContext) {

		Log.d(TAG, "Start get DynamicColorPalette");

		JSONObject colors = new JSONObject();

		Application app = this.cordova.getActivity().getApplication();
		Context context = app.getApplicationContext();

		if (Build.VERSION.SDK_INT >= 32 && DynamicColors.isDynamicColorAvailable()) {

			JSONObject colorsLight = new JSONObject();
			JSONObject colorsDark = new JSONObject();
			JSONObject colorsDayNight = new JSONObject();

			try {
				// Light
				colorsLight.put("neutral0", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral0)));
				colorsLight.put("neutral10", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral10)));
				colorsLight.put("neutral100", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral100)));
				colorsLight.put("neutral20", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral20)));
				colorsLight.put("neutral30", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral30)));
				colorsLight.put("neutral40", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral40)));
				colorsLight.put("neutral50", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral50)));
				colorsLight.put("neutral60", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral60)));
				colorsLight.put("neutral70", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral70)));
				colorsLight.put("neutral80", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral80)));
				colorsLight.put("neutral90", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral90)));
				colorsLight.put("neutral95", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral95)));
				colorsLight.put("neutral99", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral99)));
				colorsLight.put("neutralVariant0", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral_variant0)));
				colorsLight.put("neutralVariant10", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral_variant10)));
				colorsLight.put("neutralVariant100", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral_variant100)));
				colorsLight.put("neutralVariant20", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral_variant20)));
				colorsLight.put("neutralVariant30", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral_variant30)));
				colorsLight.put("neutralVariant40", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral_variant40)));
				colorsLight.put("neutralVariant50", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral_variant50)));
				colorsLight.put("neutralVariant60", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral_variant60)));
				colorsLight.put("neutralVariant70", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral_variant70)));
				colorsLight.put("neutralVariant80", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral_variant80)));
				colorsLight.put("neutralVariant90", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral_variant90)));
				colorsLight.put("neutralVariant95", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral_variant95)));
				colorsLight.put("neutralVariant99", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral_variant99)));
				colorsLight.put("primary0", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_primary0)));
				colorsLight.put("primary10", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_primary10)));
				colorsLight.put("primary100", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_primary100)));
				colorsLight.put("primary20", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_primary20)));
				colorsLight.put("primary30", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_primary30)));
				colorsLight.put("primary40", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_primary40)));
				colorsLight.put("primary50", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_primary50)));
				colorsLight.put("primary60", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_primary60)));
				colorsLight.put("primary70", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_primary70)));
				colorsLight.put("primary80", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_primary80)));
				colorsLight.put("primary90", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_primary90)));
				colorsLight.put("primary95", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_primary95)));
				colorsLight.put("primary99", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_primary99)));
				colorsLight.put("secondary0", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_secondary0)));
				colorsLight.put("secondary10", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_secondary10)));
				colorsLight.put("secondary100", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_secondary100)));
				colorsLight.put("secondary20", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_secondary20)));
				colorsLight.put("secondary30", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_secondary30)));
				colorsLight.put("secondary40", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_secondary40)));
				colorsLight.put("secondary50", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_secondary50)));
				colorsLight.put("secondary60", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_secondary60)));
				colorsLight.put("secondary70", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_secondary70)));
				colorsLight.put("secondary80", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_secondary80)));
				colorsLight.put("secondary90", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_secondary90)));
				colorsLight.put("secondary95", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_secondary95)));
				colorsLight.put("secondary99", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_secondary99)));
				colorsLight.put("tertiary0", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_tertiary0)));
				colorsLight.put("tertiary10", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_tertiary10)));
				colorsLight.put("tertiary100", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_tertiary100)));
				colorsLight.put("tertiary20", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_tertiary20)));
				colorsLight.put("tertiary30", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_tertiary30)));
				colorsLight.put("tertiary40", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_tertiary40)));
				colorsLight.put("tertiary50", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_tertiary50)));
				colorsLight.put("tertiary60", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_tertiary60)));
				colorsLight.put("tertiary70", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_tertiary70)));
				colorsLight.put("tertiary80", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_tertiary80)));
				colorsLight.put("tertiary90", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_tertiary90)));
				colorsLight.put("tertiary95", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_tertiary95)));
				colorsLight.put("tertiary99", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_tertiary99)));
				colorsLight.put("error0", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_error0)));
				colorsLight.put("error10", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_error10)));
				colorsLight.put("error100", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_error100)));
				colorsLight.put("error20", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_error20)));
				colorsLight.put("error30", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_error30)));
				colorsLight.put("error40", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_error40)));
				colorsLight.put("error50", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_error50)));
				colorsLight.put("error60", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_error60)));
				colorsLight.put("error70", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_error70)));
				colorsLight.put("error80", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_error80)));
				colorsLight.put("error90", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_error90)));
				colorsLight.put("error95", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_error95)));
				colorsLight.put("error99", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_error99)));

				// Dark
				colorsDark.put("neutral0", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral0)));
				colorsDark.put("neutral10", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral10)));
				colorsDark.put("neutral100", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral100)));
				colorsDark.put("neutral20", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral20)));
				colorsDark.put("neutral30", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral30)));
				colorsDark.put("neutral40", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral40)));
				colorsDark.put("neutral50", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral50)));
				colorsDark.put("neutral60", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral60)));
				colorsDark.put("neutral70", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral70)));
				colorsDark.put("neutral80", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral80)));
				colorsDark.put("neutral90", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral90)));
				colorsDark.put("neutral95", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral95)));
				colorsDark.put("neutral99", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral99)));
				colorsDark.put("neutralVariant0", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral_variant0)));
				colorsDark.put("neutralVariant10", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral_variant10)));
				colorsDark.put("neutralVariant100", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral_variant100)));
				colorsDark.put("neutralVariant20", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral_variant20)));
				colorsDark.put("neutralVariant30", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral_variant30)));
				colorsDark.put("neutralVariant40", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral_variant40)));
				colorsDark.put("neutralVariant50", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral_variant50)));
				colorsDark.put("neutralVariant60", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral_variant60)));
				colorsDark.put("neutralVariant70", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral_variant70)));
				colorsDark.put("neutralVariant80", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral_variant80)));
				colorsDark.put("neutralVariant90", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral_variant90)));
				colorsDark.put("neutralVariant95", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral_variant95)));
				colorsDark.put("neutralVariant99", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral_variant99)));
				colorsDark.put("primary0", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_primary0)));
				colorsDark.put("primary10", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_primary10)));
				colorsDark.put("primary100", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_primary100)));
				colorsDark.put("primary20", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_primary20)));
				colorsDark.put("primary30", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_primary30)));
				colorsDark.put("primary40", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_primary40)));
				colorsDark.put("primary50", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_primary50)));
				colorsDark.put("primary60", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_primary60)));
				colorsDark.put("primary70", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_primary70)));
				colorsDark.put("primary80", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_primary80)));
				colorsDark.put("primary90", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_primary90)));
				colorsDark.put("primary95", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_primary95)));
				colorsDark.put("primary99", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_primary99)));
				colorsDark.put("secondary0", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_secondary0)));
				colorsDark.put("secondary10", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_secondary10)));
				colorsDark.put("secondary100", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_secondary100)));
				colorsDark.put("secondary20", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_secondary20)));
				colorsDark.put("secondary30", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_secondary30)));
				colorsDark.put("secondary40", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_secondary40)));
				colorsDark.put("secondary50", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_secondary50)));
				colorsDark.put("secondary60", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_secondary60)));
				colorsDark.put("secondary70", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_secondary70)));
				colorsDark.put("secondary80", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_secondary80)));
				colorsDark.put("secondary90", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_secondary90)));
				colorsDark.put("secondary95", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_secondary95)));
				colorsDark.put("secondary99", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_secondary99)));
				colorsDark.put("tertiary0", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_tertiary0)));
				colorsDark.put("tertiary10", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_tertiary10)));
				colorsDark.put("tertiary100", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_tertiary100)));
				colorsDark.put("tertiary20", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_tertiary20)));
				colorsDark.put("tertiary30", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_tertiary30)));
				colorsDark.put("tertiary40", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_tertiary40)));
				colorsDark.put("tertiary50", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_tertiary50)));
				colorsDark.put("tertiary60", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_tertiary60)));
				colorsDark.put("tertiary70", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_tertiary70)));
				colorsDark.put("tertiary80", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_tertiary80)));
				colorsDark.put("tertiary90", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_tertiary90)));
				colorsDark.put("tertiary95", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_tertiary95)));
				colorsDark.put("tertiary99", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_tertiary99)));
				colorsDark.put("error0", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_error0)));
				colorsDark.put("error10", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_error10)));
				colorsDark.put("error100", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_error100)));
				colorsDark.put("error20", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_error20)));
				colorsDark.put("error30", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_error30)));
				colorsDark.put("error40", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_error40)));
				colorsDark.put("error50", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_error50)));
				colorsDark.put("error60", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_error60)));
				colorsDark.put("error70", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_error70)));
				colorsDark.put("error80", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_error80)));
				colorsDark.put("error90", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_error90)));
				colorsDark.put("error95", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_error95)));
				colorsDark.put("error99", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_error99)));

				// DayNight
				boolean dayNightIsDay = dayNightIsDay(app, context);

				if(dayNightIsDay) {
					colorsDayNight = colorsLight;
				} else {
					colorsDayNight = colorsDark;
				}

				colors.put("light", colorsLight);
				colors.put("dark", colorsDark);
				colors.put("dayNight", colorsDayNight);
				colors.put("theme", dayNightIsDay ? "light" : "dark");
			} catch (JSONException e) {
				callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.JSON_EXCEPTION));
				return true;
			}
		}

		Log.d(TAG, "End get DynamicColorPalette");

		callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, colors));

		return true;

	}


	private boolean getDynamicMainColor(final CallbackContext callbackContext) {

		JSONObject colors = new JSONObject();

		Application app = this.cordova.getActivity().getApplication();
		Context context = app.getApplicationContext();

		if (Build.VERSION.SDK_INT >= 32 && DynamicColors.isDynamicColorAvailable()) {
			try {
				colors.put("primary", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_light_primary)));
				colors.put("secondary", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_light_secondary)));
				colors.put("tertiary", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_light_tertiary)));
			} catch (JSONException e) {
				callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.JSON_EXCEPTION));
				return true;
			}
		}

		callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, colors));

		return true;

	}

	private boolean getDynamicColorOtherMethod(final CallbackContext callbackContext) {

		Log.d(TAG, "Start get DynamicColor");

		JSONObject colors = new JSONObject();

		Application app = this.cordova.getActivity().getApplication();
		Context context = app.getApplicationContext();
		Resources resources = app.getResources();
		String packageName = app.getPackageName();

		if (Build.VERSION.SDK_INT >= 32 && DynamicColors.isDynamicColorAvailable()) {

			Context dynamicColorContextLight = DynamicColors.wrapContextIfAvailable(context, R.style.Theme_Material3_DynamicColors_Light);
			Context dynamicColorContextDark = DynamicColors.wrapContextIfAvailable(context, R.style.Theme_Material3_DynamicColors_Dark);
			Context dynamicColorContextDayNight = DynamicColors.wrapContextIfAvailable(context, R.style.Theme_Material3_DynamicColors_DayNight);

			// Define attributes to resolve in an array
			int[] attrsToResolve = {
				R.attr.colorPrimary,
				R.attr.colorOnPrimary,
				R.attr.colorPrimaryContainer,
				R.attr.colorOnPrimaryContainer,
				R.attr.colorSecondary,
				R.attr.colorOnSecondary,
				R.attr.colorSecondaryContainer,
				R.attr.colorOnSecondaryContainer,
				R.attr.colorTertiary,
				R.attr.colorOnTertiary,
				R.attr.colorTertiaryContainer,
				R.attr.colorOnTertiaryContainer,
				R.attr.colorError,
				R.attr.colorOnError,
				R.attr.colorErrorContainer,
				R.attr.colorOnErrorContainer,
				R.attr.colorOutline,
				R.attr.colorBackgroundFloating,
				R.attr.colorOnBackground,
				R.attr.colorSurface,
				R.attr.colorOnSurface,
				R.attr.colorSurfaceVariant,
				R.attr.colorOnSurfaceVariant,
				R.attr.colorSurfaceInverse,
				R.attr.colorOnSurfaceInverse,
				R.attr.colorPrimaryInverse,
				resources.getIdentifier("colorShadow", "attr", packageName),
				resources.getIdentifier("colorSurfaceTint", "attr", packageName),
				R.attr.colorOutlineVariant,
				resources.getIdentifier("colorScrim", "attr", packageName),
			};

			// Resolve them
			TypedArray taLight = dynamicColorContextLight.obtainStyledAttributes(attrsToResolve);
			TypedArray taDark = dynamicColorContextDark.obtainStyledAttributes(attrsToResolve);
			TypedArray taDayNight = dynamicColorContextDayNight.obtainStyledAttributes(attrsToResolve);

			JSONObject colorsLight = new JSONObject();
			JSONObject colorsDark = new JSONObject();
			JSONObject colorsDayNight = new JSONObject();

			try {
				// Light
				colorsLight.put("primary", intColorToHex(taLight.getColor(0, 0)));
				colorsLight.put("onPrimary", intColorToHex(taLight.getColor(1, 0)));
				colorsLight.put("primaryContainer", intColorToHex(taLight.getColor(2, 0)));
				colorsLight.put("onPrimaryContainer", intColorToHex(taLight.getColor(3, 0)));
				colorsLight.put("secondary", intColorToHex(taLight.getColor(4, 0)));
				colorsLight.put("onSecondary", intColorToHex(taLight.getColor(5, 0)));
				colorsLight.put("secondaryContainer", intColorToHex(taLight.getColor(6, 0)));
				colorsLight.put("onSecondaryContainer", intColorToHex(taLight.getColor(7, 0)));
				colorsLight.put("tertiary", intColorToHex(taLight.getColor(8, 0)));
				colorsLight.put("onTertiary", intColorToHex(taLight.getColor(9, 0)));
				colorsLight.put("tertiaryContainer", intColorToHex(taLight.getColor(10, 0)));
				colorsLight.put("onTertiaryContainer", intColorToHex(taLight.getColor(11, 0)));
				colorsLight.put("error", intColorToHex(taLight.getColor(12, 0)));
				colorsLight.put("onError", intColorToHex(taLight.getColor(13, 0)));
				colorsLight.put("errorContainer", intColorToHex(taLight.getColor(14, 0)));
				colorsLight.put("onErrorContainer", intColorToHex(taLight.getColor(15, 0)));
				colorsLight.put("outline", intColorToHex(taLight.getColor(16, 0)));
				colorsLight.put("background", intColorToHex(taLight.getColor(17, 0)));
				colorsLight.put("onBackground", intColorToHex(taLight.getColor(18, 0)));
				colorsLight.put("surface", intColorToHex(taLight.getColor(19, 0)));
				colorsLight.put("onSurface", intColorToHex(taLight.getColor(20, 0)));
				colorsLight.put("surfaceVariant", intColorToHex(taLight.getColor(21, 0)));
				colorsLight.put("onSurfaceVariant", intColorToHex(taLight.getColor(22, 0)));
				colorsLight.put("inverseSurface", intColorToHex(taLight.getColor(23, 0)));
				colorsLight.put("inverseOnSurface", intColorToHex(taLight.getColor(24, 0)));
				colorsLight.put("inversePrimary", intColorToHex(taLight.getColor(25, 0)));
				colorsLight.put("shadow", intColorToHex(taLight.getColor(26, 0)));
				colorsLight.put("surfaceTint", intColorToHex(taLight.getColor(27, 0)));
				colorsLight.put("outlineVariant", intColorToHex(taLight.getColor(28, 0)));
				colorsLight.put("scrim", intColorToHex(taLight.getColor(29, 0)));

				// Dark
				colorsDark.put("primary", intColorToHex(taDark.getColor(0, 0)));
				colorsDark.put("onPrimary", intColorToHex(taDark.getColor(1, 0)));
				colorsDark.put("primaryContainer", intColorToHex(taDark.getColor(2, 0)));
				colorsDark.put("onPrimaryContainer", intColorToHex(taDark.getColor(3, 0)));
				colorsDark.put("secondary", intColorToHex(taDark.getColor(4, 0)));
				colorsDark.put("onSecondary", intColorToHex(taDark.getColor(5, 0)));
				colorsDark.put("secondaryContainer", intColorToHex(taDark.getColor(6, 0)));
				colorsDark.put("onSecondaryContainer", intColorToHex(taDark.getColor(7, 0)));
				colorsDark.put("tertiary", intColorToHex(taDark.getColor(8, 0)));
				colorsDark.put("onTertiary", intColorToHex(taDark.getColor(9, 0)));
				colorsDark.put("tertiaryContainer", intColorToHex(taDark.getColor(10, 0)));
				colorsDark.put("onTertiaryContainer", intColorToHex(taDark.getColor(11, 0)));
				colorsDark.put("error", intColorToHex(taDark.getColor(12, 0)));
				colorsDark.put("onError", intColorToHex(taDark.getColor(13, 0)));
				colorsDark.put("errorContainer", intColorToHex(taDark.getColor(14, 0)));
				colorsDark.put("onErrorContainer", intColorToHex(taDark.getColor(15, 0)));
				colorsDark.put("outline", intColorToHex(taDark.getColor(16, 0)));
				colorsDark.put("background", intColorToHex(taDark.getColor(17, 0)));
				colorsDark.put("onBackground", intColorToHex(taDark.getColor(18, 0)));
				colorsDark.put("surface", intColorToHex(taDark.getColor(19, 0)));
				colorsDark.put("onSurface", intColorToHex(taDark.getColor(20, 0)));
				colorsDark.put("surfaceVariant", intColorToHex(taDark.getColor(21, 0)));
				colorsDark.put("onSurfaceVariant", intColorToHex(taDark.getColor(22, 0)));
				colorsDark.put("inverseSurface", intColorToHex(taDark.getColor(23, 0)));
				colorsDark.put("inverseOnSurface", intColorToHex(taDark.getColor(24, 0)));
				colorsDark.put("inversePrimary", intColorToHex(taDark.getColor(25, 0)));
				colorsDark.put("shadow", intColorToHex(taDark.getColor(26, 0)));
				colorsDark.put("surfaceTint", intColorToHex(taDark.getColor(27, 0)));
				colorsDark.put("outlineVariant", intColorToHex(taDark.getColor(28, 0)));
				colorsDark.put("scrim", intColorToHex(taDark.getColor(29, 0)));

				// DayNight
				colorsDayNight.put("primary", intColorToHex(taDayNight.getColor(0, 0)));
				colorsDayNight.put("onPrimary", intColorToHex(taDayNight.getColor(1, 0)));
				colorsDayNight.put("primaryContainer", intColorToHex(taDayNight.getColor(2, 0)));
				colorsDayNight.put("onPrimaryContainer", intColorToHex(taDayNight.getColor(3, 0)));
				colorsDayNight.put("secondary", intColorToHex(taDayNight.getColor(4, 0)));
				colorsDayNight.put("onSecondary", intColorToHex(taDayNight.getColor(5, 0)));
				colorsDayNight.put("secondaryContainer", intColorToHex(taDayNight.getColor(6, 0)));
				colorsDayNight.put("onSecondaryContainer", intColorToHex(taDayNight.getColor(7, 0)));
				colorsDayNight.put("tertiary", intColorToHex(taDayNight.getColor(8, 0)));
				colorsDayNight.put("onTertiary", intColorToHex(taDayNight.getColor(9, 0)));
				colorsDayNight.put("tertiaryContainer", intColorToHex(taDayNight.getColor(10, 0)));
				colorsDayNight.put("onTertiaryContainer", intColorToHex(taDayNight.getColor(11, 0)));
				colorsDayNight.put("error", intColorToHex(taDayNight.getColor(12, 0)));
				colorsDayNight.put("onError", intColorToHex(taDayNight.getColor(13, 0)));
				colorsDayNight.put("errorContainer", intColorToHex(taDayNight.getColor(14, 0)));
				colorsDayNight.put("onErrorContainer", intColorToHex(taDayNight.getColor(15, 0)));
				colorsDayNight.put("outline", intColorToHex(taDayNight.getColor(16, 0)));
				colorsDayNight.put("background", intColorToHex(taDayNight.getColor(17, 0)));
				colorsDayNight.put("onBackground", intColorToHex(taDayNight.getColor(18, 0)));
				colorsDayNight.put("surface", intColorToHex(taDayNight.getColor(19, 0)));
				colorsDayNight.put("onSurface", intColorToHex(taDayNight.getColor(20, 0)));
				colorsDayNight.put("surfaceVariant", intColorToHex(taDayNight.getColor(21, 0)));
				colorsDayNight.put("onSurfaceVariant", intColorToHex(taDayNight.getColor(22, 0)));
				colorsDayNight.put("inverseSurface", intColorToHex(taDayNight.getColor(23, 0)));
				colorsDayNight.put("inverseOnSurface", intColorToHex(taDayNight.getColor(24, 0)));
				colorsDayNight.put("inversePrimary", intColorToHex(taDayNight.getColor(25, 0)));
				colorsDayNight.put("shadow", intColorToHex(taDayNight.getColor(26, 0)));
				colorsDayNight.put("surfaceTint", intColorToHex(taDayNight.getColor(27, 0)));
				colorsDayNight.put("outlineVariant", intColorToHex(taDayNight.getColor(28, 0)));
				colorsDayNight.put("scrim", intColorToHex(taDayNight.getColor(29, 0)));

				colors.put("light", colorsLight);
				colors.put("dark", colorsDark);
				colors.put("dayNight", colorsDayNight);
			} catch (JSONException e) {
				callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.JSON_EXCEPTION));
				return true;
			}

			// recycle TypedArray
			taLight.recycle();
			taDark.recycle();	 
			taDayNight.recycle();
		}

		Log.d(TAG, "End get DynamicColor");

		callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, colors));

		return true;

	}

}
