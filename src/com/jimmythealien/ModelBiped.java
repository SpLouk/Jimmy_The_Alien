package com.jimmythealien.src;

import java.awt.Point;

public class ModelBiped extends Model {

	protected boolean runVar;
	protected int lCalfAng = 0, rCalfAng = 0, armAng = 0;

	public ModelBiped(EntityLiving e) {
		super(e);
	}

	protected void calculateAngs() {
		switch (getActInt()) {
		case 0:

			if (leftLegAng > 0) {
				leftLegAng -= 30;
			} else if (leftLegAng < 0) {
				leftLegAng = 0;
			}

			if (rightLegAng > 0) {
				rightLegAng -= 30;
			} else if (rightLegAng < 0) {
				rightLegAng = 0;
			}

			if (lCalfAng > 0) {
				lCalfAng -= 30;
			} else if (lCalfAng < 0) {
				lCalfAng = 0;
			}

			if (rCalfAng > 0) {
				rCalfAng -= 30;
			} else if (rCalfAng < 0) {
				rCalfAng = 0;
			}

			if (armAng > 0) {
				armAng -= 30;
			} else if (armAng < 0) {
				armAng = 0;
			}

			break;

		case 1:
			running();
			break;

		case 2:
			jumping();
			break;

		case 3:

			break;
		}
	}

	protected void running() {

		if (runVar) {
			rightLegAng += 15;
			leftLegAng = rightLegAng * -1;

			switch (getOrientation()) {
			case 0: // right leg is swinging back.
				if (rightLegAng >= 0) {
					if (iL > 0) {
						iL -= 15;
					}

					if (rightLegAng >= 45) {
						if (iL > 0) {
							iL -= 15;
						}
					}
				}

				if (rightLegAng <= 0) {
					if (iL < 120) {
						iL += 30;
					}

				}

				break;

			case 2: // right leg is swinging forward.
				if (rightLegAng >= 0) {
					if (iR < 0) {
						iR += 15;
					}

					if (rightLegAng >= 45) {
						if (iR < 0) {
							iR += 15;
						}
					}
				}

				if (rightLegAng <= 0) {
					if (iR > -120) {
						iR -= 30;
					}
				}

				break;

			}

			if (rightLegAng == 90) {
				runVar = false;
			}

		} else {
			rightLegAng -= 15;
			leftLegAng = rightLegAng * -1;

			switch (getOrientation()) {
			case 0: // right leg is swinging forward.

				if (rightLegAng <= 0) {
					if (iR > 0) {
						iR -= 15;
					}

					if (rightLegAng <= 45) {
						if (iR > 0) {
							iR -= 15;
						}
					}
				}

				if (rightLegAng >= 0) {
					if (iR < 120) {
						iR += 30;
					}
				}
				break;

			case 2: // right leg is swinging back.

				if (rightLegAng <= 0) {
					if (iL < 0) {
						iL += 15;
					}

					if (rightLegAng <= 45) {
						if (iL < 0) {
							iL += 15;
						}
					}
				}

				if (rightLegAng >= 0) {
					if (iL > -120) {
						iL -= 30;
					}
				}

				break;

			}

			if (rightLegAng == -90) {
				runVar = true;
			}
		}

		lCalfAng = leftLegAng + iL;
		rCalfAng = rightLegAng + iR;

	}

	protected void jumping() {

		if (armAng < 120) {
			armAng += 15;
		}
	}

	protected Point getRotatePoint(Point p, int angle, int length) {

		double radAng = Math.toRadians(angle);

		int y = Math.round((float) (length * Math.cos(radAng)));

		int x = Math.round((float) (length * Math.sin(radAng)));

		return new Point(p.x + x, p.y + y);

	}
}
