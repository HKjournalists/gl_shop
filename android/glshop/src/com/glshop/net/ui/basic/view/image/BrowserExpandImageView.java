package com.glshop.net.ui.basic.view.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Gallery;
import android.widget.RelativeLayout;

import com.glshop.net.R;

public class BrowserExpandImageView extends RelativeLayout {

	// 大图显示
	public BrowserImageView image;

	public BrowserExpandImageView(Context context) {
		super(context);
		Init();
	}

	private void Init() {
		setLayoutParams(new Gallery.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

		RelativeLayout.LayoutParams i_params = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		image = new BrowserImageView(getContext());
		image.setLayoutParams(i_params);
		this.addView(image);
	}

	public void setImageBitmap(Bitmap bitmap) {
		image.setImageBitmap(bitmap);
	}

	public void setDefaultImage() {
		image.setImageResource(R.drawable.bg_banner_advert_1);
	}

}