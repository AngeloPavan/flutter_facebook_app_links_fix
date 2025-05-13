package it.remedia.flutter_facebook_app_links;

import android.net.Uri;
import androidx.annotation.NonNull;
import com.facebook.applinks.AppLinkData;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

public class FlutterFacebookAppLinksPlugin
    implements FlutterPlugin, MethodCallHandler {
  private MethodChannel channel;
  private FlutterPluginBinding binding;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding binding) {
    this.binding = binding;
    channel = new MethodChannel(binding.getBinaryMessenger(), "flutter_facebook_app_links");
    channel.setMethodCallHandler(this);
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    if (call.method.equals("getDeferredAppLink")) {
      AppLinkData.fetchDeferredAppLinkData(
        binding.getApplicationContext(),
        new AppLinkData.CompletionHandler() {
          @Override
          public void onDeferredAppLinkDataFetched(AppLinkData appLinkData) {
            Uri link = appLinkData != null ? appLinkData.getTargetUri() : null;
            result.success(link != null ? link.toString() : null);
          }
        }
      );
    } else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
    channel = null;
  }
}
