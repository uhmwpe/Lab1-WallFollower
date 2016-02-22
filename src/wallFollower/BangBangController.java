package wallFollower;
import lejos.hardware.motor.*;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.TextLCD;


public class BangBangController implements UltrasonicController{
	private final int bandCenter, bandwidth;
	private final int motorLow, motorHigh;
	private int distance, gap; 
	//public final int distance2;
	private EV3LargeRegulatedMotor leftMotor, rightMotor;
	
	public BangBangController(EV3LargeRegulatedMotor leftMotor, EV3LargeRegulatedMotor rightMotor,
							  int bandCenter, int bandwidth, int motorLow, int motorHigh) {
		//Default Constructor
		this.bandCenter = bandCenter;
		this.bandwidth = bandwidth;
		this.motorLow = motorLow;
		this.motorHigh = motorHigh;
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
		gap = 0;
		leftMotor.setSpeed(motorHigh);				// Start robot moving forward
		rightMotor.setSpeed(motorHigh);
		leftMotor.forward();
		rightMotor.forward();
		
	}
	
	@Override
	public void processUSData(int distance){ //reads distance from sensor to wall
		
			this.distance = distance;
			//System.out.println(this.distance);
			if(Math.abs(this.distance - bandCenter) > bandwidth){ //needs to turn somewhere
				if (this.distance < bandCenter){//needs to turn right
					leftMotor.setSpeed(125);
					rightMotor.setSpeed(70);
					leftMotor.forward();
					rightMotor.backward(); //one wheel turns backwards to make sure it doesn't hit a wall as it's adjusting
				}
				else{ //needs to turn left
					leftMotor.setSpeed(70);
					rightMotor.setSpeed(125);
					leftMotor.backward();
					rightMotor.forward();
				}
				/*leftMotor.setSpeed(motorHigh);
				rightMotor.setSpeed(motorHigh);
				leftMotor.forward();
				rightMotor.backward();*/
			
			}
			else{ //can go straight
				leftMotor.setSpeed(100);
				rightMotor.setSpeed(100);
				leftMotor.forward();
				rightMotor.forward();
				
			
			
		}
			
		
			/*if (Math.abs(distance2 - bandCenter) > bandwidth){ //distance is higher than tolerable level
			
				/*if(distance<bandCenter){
					leftMotor.setSpeed(motorHigh);
					rightMotor.setSpeed(motorLow);
					leftMotor.forward();
					rightMotor.forward();
				}
		
				else{
					leftMotor.setSpeed(motorLow);
					rightMotor.setSpeed(motorHigh);
					leftMotor.forward();
					rightMotor.forward();
				}*/
				//if (gap>20){ // if the gap is larger than 20 spaces
				
					/*if (distance2<bandCenter){
						/*if (Math.abs(distance2 - bandCenter) > 2*bandwidth){//turns wheels in different directions
							//when distance from bandwidth is large
							leftMotor.setSpeed(motorHigh);
							rightMotor.setSpeed(motorHigh);
							leftMotor.forward();
							rightMotor.backward();
							processUSData(distance2);
							gap-=5;
						}
						else{*/
						/*	leftMotor.setSpeed(motorHigh);
							rightMotor.setSpeed(motorLow);
							leftMotor.forward();
							rightMotor.forward();
							//processUSData(distance2);
						
						/*}
				
					else if (distance2 > bandCenter){
						/*if(Math.abs(distance2-bandCenter) > 2*bandwidth){ //turns wheels in different directions 
							//when distance from bandwidth is large
							leftMotor.setSpeed(motorHigh);
							rightMotor.setSpeed(motorHigh);
							leftMotor.backward();
							rightMotor.forward();
							processUSData(distance2);
							gap-=5;
						}
						else{*/
					/*		leftMotor.setSpeed(motorLow);
							rightMotor.setSpeed(motorHigh);
							leftMotor.forward();
							rightMotor.forward();
							//processUSData(distance2);
						
						}
					
				
				
					/*leftMotor.setSpeed(motorHigh);
					rightMotor.setSpeed(motorHigh);
					leftMotor.backward();
					rightMotor.forward();
					gap = 0; */
					//}
			
				
					//}
					/*else{
						leftMotor.setSpeed(motorHigh);
						rightMotor.setSpeed(motorHigh);
						leftMotor.forward();
						rightMotor.forward();
						//processUSData(distance);
					}
			}
		}
		
		*/
		
		

		// TODO: process a movement based on the us distance passed in (BANG-BANG style)
	}

	@Override
	public int readUSDistance() {
		return this.distance;
	}
}
