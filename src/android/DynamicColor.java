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
import android.content.res.Resources;
import android.os.Build;
import android.util.Log;

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
					colorsLight.put("surfaceTint", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_light_primary))); //intColorToHex(ContextCompat.getColor(context, R.color.m3_assist_chip_icon_tint_color)));
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
					colorsDark.put("surfaceTint", intColorToHex(ContextCompat.getColor(context, R.color.m3_sys_color_dynamic_dark_primary))); //intColorToHex(ContextCompat.getColor(context, R.color.m3_assist_chip_icon_tint_color)));
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

			JSONObject palette = new JSONObject();

			try {
				// Light
				palette.put("neutral0", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral0)));
				palette.put("neutral10", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral10)));
				palette.put("neutral100", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral100)));
				palette.put("neutral20", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral20)));
				palette.put("neutral30", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral30)));
				palette.put("neutral40", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral40)));
				palette.put("neutral50", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral50)));
				palette.put("neutral60", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral60)));
				palette.put("neutral70", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral70)));
				palette.put("neutral80", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral80)));
				palette.put("neutral90", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral90)));
				palette.put("neutral95", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral95)));
				palette.put("neutral99", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral99)));
				palette.put("neutralVariant0", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral_variant0)));
				palette.put("neutralVariant10", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral_variant10)));
				palette.put("neutralVariant100", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral_variant100)));
				palette.put("neutralVariant20", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral_variant20)));
				palette.put("neutralVariant30", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral_variant30)));
				palette.put("neutralVariant40", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral_variant40)));
				palette.put("neutralVariant50", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral_variant50)));
				palette.put("neutralVariant60", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral_variant60)));
				palette.put("neutralVariant70", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral_variant70)));
				palette.put("neutralVariant80", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral_variant80)));
				palette.put("neutralVariant90", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral_variant90)));
				palette.put("neutralVariant95", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral_variant95)));
				palette.put("neutralVariant99", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_neutral_variant99)));
				palette.put("primary0", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_primary0)));
				palette.put("primary10", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_primary10)));
				palette.put("primary100", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_primary100)));
				palette.put("primary20", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_primary20)));
				palette.put("primary30", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_primary30)));
				palette.put("primary40", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_primary40)));
				palette.put("primary50", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_primary50)));
				palette.put("primary60", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_primary60)));
				palette.put("primary70", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_primary70)));
				palette.put("primary80", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_primary80)));
				palette.put("primary90", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_primary90)));
				palette.put("primary95", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_primary95)));
				palette.put("primary99", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_primary99)));
				palette.put("secondary0", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_secondary0)));
				palette.put("secondary10", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_secondary10)));
				palette.put("secondary100", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_secondary100)));
				palette.put("secondary20", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_secondary20)));
				palette.put("secondary30", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_secondary30)));
				palette.put("secondary40", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_secondary40)));
				palette.put("secondary50", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_secondary50)));
				palette.put("secondary60", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_secondary60)));
				palette.put("secondary70", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_secondary70)));
				palette.put("secondary80", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_secondary80)));
				palette.put("secondary90", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_secondary90)));
				palette.put("secondary95", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_secondary95)));
				palette.put("secondary99", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_secondary99)));
				palette.put("tertiary0", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_tertiary0)));
				palette.put("tertiary10", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_tertiary10)));
				palette.put("tertiary100", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_tertiary100)));
				palette.put("tertiary20", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_tertiary20)));
				palette.put("tertiary30", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_tertiary30)));
				palette.put("tertiary40", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_tertiary40)));
				palette.put("tertiary50", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_tertiary50)));
				palette.put("tertiary60", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_tertiary60)));
				palette.put("tertiary70", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_tertiary70)));
				palette.put("tertiary80", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_tertiary80)));
				palette.put("tertiary90", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_tertiary90)));
				palette.put("tertiary95", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_tertiary95)));
				palette.put("tertiary99", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_dynamic_tertiary99)));
				palette.put("error0", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_error0)));
				palette.put("error10", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_error10)));
				palette.put("error100", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_error100)));
				palette.put("error20", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_error20)));
				palette.put("error30", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_error30)));
				palette.put("error40", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_error40)));
				palette.put("error50", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_error50)));
				palette.put("error60", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_error60)));
				palette.put("error70", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_error70)));
				palette.put("error80", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_error80)));
				palette.put("error90", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_error90)));
				palette.put("error95", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_error95)));
				palette.put("error99", intColorToHex(ContextCompat.getColor(context, R.color.m3_ref_palette_error99)));

				// DayNight
				boolean dayNightIsDay = dayNightIsDay(app, context);

				colors.put("palette", palette);
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

}