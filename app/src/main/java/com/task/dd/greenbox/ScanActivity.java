package com.task.dd.greenbox;

import android.app.Activity;
import android.os.Bundle;

import com.uuzuche.lib_zxing.activity.ZXingLibrary;

/**二维码扫描
 * Created by dd on 2016/12/15.
 */

public class ScanActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ZXingLibrary.initDisplayOpinion(this);
    }
}
