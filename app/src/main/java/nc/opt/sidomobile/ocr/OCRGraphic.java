/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nc.opt.sidomobile.ocr;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import nc.opt.sidomobile.barcodreader.camera.GraphicOverlay;

/**
 * Graphic instance for rendering barcode position, size, and ID within an associated graphic
 * overlay view.
 */
public class OCRGraphic extends GraphicOverlay.Graphic {

    private int mId;

    private Paint mRectPaint;
    private Paint mTextPaint;
    private volatile FirebaseVisionText.TextBlock mTextBlock;

    public OCRGraphic(GraphicOverlay overlay) {
        super(overlay);

        final int selectedColor = Color.GREEN;

        mRectPaint = new Paint();
        mRectPaint.setColor(selectedColor);
        mRectPaint.setStyle(Paint.Style.STROKE);
        mRectPaint.setStrokeWidth(4.0f);

        mTextPaint = new Paint();
        mTextPaint.setColor(selectedColor);
        mTextPaint.setTextSize(36.0f);
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public FirebaseVisionText.TextBlock getTextBlock() {
        return mTextBlock;
    }

    public void setTextBlock(FirebaseVisionText.TextBlock mBarcode) {
        this.mTextBlock = mBarcode;
    }

    /**
     * Updates the barcode instance from the detection of the most recent frame.  Invalidates the
     * relevant portions of the overlay to trigger a redraw.
     */
    void updateItem(FirebaseVisionText.TextBlock barcode) {
        mTextBlock = barcode;
        postInvalidate();
    }

    /**
     * Draws the barcode annotations for position, size, and raw value on the supplied canvas.
     */
    @Override
    public void draw(Canvas canvas) {
        FirebaseVisionText.TextBlock textblock = mTextBlock;
        if (textblock == null) {
            return;
        }

        // Draws the bounding box around the textblock.
        RectF rect = new RectF(textblock.getBoundingBox());
        rect.left = translateX(rect.left);
        rect.top = translateY(rect.top);
        rect.right = translateX(rect.right);
        rect.bottom = translateY(rect.bottom);
        canvas.drawRect(rect, mRectPaint);

        // Draws a label at the bottom of the textblock indicate the textblock value that was detected.
        canvas.drawText(textblock.getText(), rect.left, rect.bottom, mTextPaint);
    }
}
