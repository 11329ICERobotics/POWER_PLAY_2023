package org.firstinspires.ftc.teamcode.subsystems;

import com.fizzyapple12.javadi.DiContainer;
import com.fizzyapple12.javadi.DiInterfaces;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.robot.Robot;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RobotConfig;

public class Claw implements DiInterfaces.IDisposable, DiInterfaces.IInitializable {
    @DiContainer.Inject(id = "clawServo")
    Servo closeClaw;
    @DiContainer.Inject(id = "handServo1")
    public Servo handWave1;
    @DiContainer.Inject(id = "handServo2")
    public Servo handWave2;
    @DiContainer.Inject()
    Telemetry telemetry;
    private boolean grabbing = false;
    private boolean grabbingDebounce = false;

    @Override
    public void onInitialize() {

    }

    public void setClawPower(double armSpeed) {
        setPos(handWave1.getPosition() + RobotConfig.Arm.armSpeed * armSpeed);
        //handWave1.setPosition(handWave1.getPosition() + RobotConfig.Claw.handSpeed * armSpeed);
        //handWave2.setPosition(handWave2.getPosition() - RobotConfig.Claw.handSpeed * armSpeed);
    }

    public void setPos(double pos) {
        handWave2.setPosition(1.0 - pos);
        handWave1.setPosition(pos);
    }

    public void toggle() {
        if (grabbing && !grabbingDebounce) {
            ungrab();
        } else if (!grabbing && !grabbingDebounce) {
            grab();
        }
        grabbingDebounce = true;
    }

    public void resetToggle() {
        grabbingDebounce = false;
    }

    public void grab() {
        grabbing = true;
        closeClaw.setPosition(RobotConfig.Claw.closePos);
    }

    public void ungrab() {
        grabbing = false;
        closeClaw.setPosition(RobotConfig.Claw.openPos);
    }
    public void displayToTelemetry() {
        telemetry.addData("Hand1 Position", handWave1.getPosition());
        telemetry.addData("Hand2 Position", handWave2.getPosition());
    }
    @Override
    public void onDispose() {

    }
}
