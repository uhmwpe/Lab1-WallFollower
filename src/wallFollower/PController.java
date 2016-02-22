package wallFollower;
import lejos.hardware.motor.EV3LargeRegulatedMotor;

public class PController implements UltrasonicController {
	
	private final int bandCenter, bandwidth;
	private final int motorStraight = 200, FILTER_OUT = 20;
	private EV3LargeRegulatedMotor leftMotor, rightMotor;
	private int distance;
	private int filterControl;
	
	public PController(EV3LargeRegulatedMotor leftMotor, EV3LargeRegulatedMotor rightMotor,
					   int bandCenter, int bandwidth) {
		//Default Constructor
		this.bandCenter = bandCenter;
		this.bandwidth = bandwidth;
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
		leftMotor.setSpeed(motorStraight);					// Initalize motor rolling forward
		rightMotor.setSpeed(motorStraight);
		leftMotor.forward();
		rightMotor.forward();
		filterControl = 0;
	}
	
	@Override
	public void processUSData(int distance) {
		
		// rudimentary filter - toss out invalid samples corresponding to null signal.
		// (n.b. this was not included in the Bang-bang controller, but easily could have).
		//
		if (distance == 255 && filterControl < FILTER_OUT) {
			// bad value, do not set the distance var, however do increment the filter value
			filterControl ++;
		} else if (distance == 255){
			// true 255, therefore set distance to 255
			this.distance = distance;
		} else {
			// distance went below 255, therefore reset everything.
			filterControl = 0;
			this.distance = distance;
		}
		int error;
		error = Math.abs(this.distance - bandCenter);
		double deltaspd2;
		deltaspd2 = 20 / 1 + Math.pow(2.718281828, -1*(error-10)); //logistic function with max 20 as to ensure it doesn't turn too fast at any point. 
		int deltaspd = (int) deltaspd2;
		 
		
		if (Math.abs(this.distance-bandCenter)>bandwidth){
			if (distance<bandCenter){ //needs to turn right
				leftMotor.setSpeed(100 + deltaspd);
				rightMotor.setSpeed(60- deltaspd); //the backward wheel rotates faster with proportion to error
				leftMotor.forward();
				rightMotor.forward();
			}
			else if (this.distance>bandCenter){ //needs to turn left
				leftMotor.setSpeed(60 - deltaspd); //backward wheel rotates faster with proportion to error
				rightMotor.setSpeed(100 + deltaspd);
				leftMotor.forward();
				rightMotor.forward();
			}
		}
		else{ //can go straight
			leftMotor.setSpeed(100);
			rightMotor.setSpeed(100);
			leftMotor.forward();
			rightMotor.forward();
		}
		
		// TODO: process a movement based on the us distance passed in (P style)	
	}


	@Override
	public int readUSDistance() {
		return this.distance;
	}

}
