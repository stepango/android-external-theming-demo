package com.example.DownloadableThemes;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyActivity extends Activity {

  final static String[] NAMES = new String[]{
      "ic_1", "ic_2", "ic_3", "ic_4", "ic_5",
      "ic_6", "ic_7", "ic_8", "ic_9", "ic_10"
  };

  private String mResourcesPackageName;

  private Resources mThemeResources;
  private Context mThemeContext;
  private LayoutInflater mInflater;

  /**
   * Called when the activity is first created.
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    try {
      mResourcesPackageName = "com.legion.theme.MetroTheme";
      mThemeContext = createPackageContext("com.legion.theme.MetroTheme", CONTEXT_IGNORE_SECURITY);
      mThemeResources = mThemeContext.getResources();
    } catch (PackageManager.NameNotFoundException e) {
      mThemeResources = getResources();
      mResourcesPackageName = getPackageName();
      mThemeContext = this;
    }
    mInflater = (LayoutInflater) mThemeContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    setContentView("main");
    AbsListView view = (AbsListView) findViewById("list");
    view.setCacheColorHint(getColorByName("list_background"));
    view.setBackgroundColor(getColorByName("list_background"));
    view.setAdapter(new MyAdapter());
  }

  private int getColorByName(final String resName) {
    return mThemeResources.getColor(mThemeResources.getIdentifier(resName, "color", getResourcesPackageName()));
  }

  private View findViewById(final String resName) {
    return findViewById(getIdResId(resName));
  }

  private void setContentView(final String resName) {
    setContentView(mInflater.inflate(getLayoutResId(resName), null));
  }

  /**
   * @param resName name of drawable resource. Example "ic_1" for ic_1.png file.
   * @return id
   */
  private int getDrawableResId(final String resName) {
    return mThemeResources.getIdentifier(resName, "drawable", getResourcesPackageName());
  }

  private int getLayoutResId(final String resName) {
    return mThemeResources.getIdentifier(resName, "layout", getResourcesPackageName());
  }

  private int getIdResId(final String resName) {
    return mThemeResources.getIdentifier(resName, "id", getResourcesPackageName());
  }

  public String getResourcesPackageName() {
    return mResourcesPackageName;
  }

  private class MyAdapter extends BaseAdapter {

    @Override
    public int getCount() {
      return NAMES.length;
    }

    @Override
    public Object getItem(final int position) {
      return NAMES[position];
    }

    @Override
    public long getItemId(final int position) {
      return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
      ViewHolder holder;

      if (convertView == null) {
        convertView = mInflater.inflate(mThemeResources.getIdentifier("list_item", "layout", mResourcesPackageName), null);

        holder = new ViewHolder();
        holder.title = (TextView) convertView.findViewById(getIdResId("title"));
        holder.icon = (ImageView) convertView.findViewById(getIdResId("icon"));

        convertView.setTag(holder);
      } else {
        holder = (ViewHolder) convertView.getTag();
      }

      holder.title.setText(NAMES[position]);
      holder.icon.setImageBitmap(BitmapFactory.decodeResource(mThemeResources, getDrawableResId(NAMES[position])));

      return convertView;
    }

    private class ViewHolder {
      public TextView title;
      public ImageView icon;
    }
  }
}
