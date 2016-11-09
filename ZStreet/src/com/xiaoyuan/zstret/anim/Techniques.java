/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 daimajia
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.xiaoyuan.zstret.anim;

import com.xiaoyuan.zstret.anim.animator.BounceAnimator;
import com.xiaoyuan.zstret.anim.animator.DropOutAnimator;
import com.xiaoyuan.zstret.anim.animator.FadeInUpAnimator;
import com.xiaoyuan.zstret.anim.animator.FadeOutDownAnimator;
import com.xiaoyuan.zstret.anim.animator.FadeOutUpAnimator;
import com.xiaoyuan.zstret.anim.animator.FlashAnimator;
import com.xiaoyuan.zstret.anim.animator.PulseAnimator;
import com.xiaoyuan.zstret.anim.animator.RubberBandAnimator;
import com.xiaoyuan.zstret.anim.animator.ShakeAnimator;
import com.xiaoyuan.zstret.anim.animator.StandUpAnimator;
import com.xiaoyuan.zstret.anim.animator.SwingAnimator;
import com.xiaoyuan.zstret.anim.animator.TadaAnimator;
import com.xiaoyuan.zstret.anim.animator.WaveAnimator;
import com.xiaoyuan.zstret.anim.animator.WobbleAnimator;

public enum Techniques {

	Flash(FlashAnimator.class), Pulse(PulseAnimator.class), RubberBand(
			RubberBandAnimator.class), Shake(ShakeAnimator.class), Swing(
			SwingAnimator.class), Wobble(WobbleAnimator.class), Bounce(
			BounceAnimator.class), Tada(TadaAnimator.class), StandUp(
			StandUpAnimator.class), Wave(WaveAnimator.class), FadeInUp(
			FadeInUpAnimator.class), FadeOutDown(FadeOutDownAnimator.class), DropOut(
			DropOutAnimator.class), FadeOutUp(FadeOutUpAnimator.class);

	private Class animatorClazz;

	private Techniques(Class clazz) {
		animatorClazz = clazz;
	}

	public BaseViewAnimator getAnimator() {
		try {
			return (BaseViewAnimator) animatorClazz.newInstance();
		} catch (Exception e) {
			throw new Error("Can not init animatorClazz instance");
		}
	}
}
