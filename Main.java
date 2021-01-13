import com.sun.jna.*;

import com.sun.jna.platform.win32.WinDef.HWND;

import com.sun.jna.platform.win32.WinUser;

import com.sun.jna.win32.*;

import java.util.function.Consumer;

interface User32 extends StdCallLibrary {
    // Method declarations, constant and structure definitions go here
    HWND FindWindowA(String lpClassName, String lpWindowName);

    boolean SetForegroundWindow(HWND hWnd);

    boolean ShowWindow(HWND hWnd, int nCmdShow);
}

public class Main {

    static User32 INSTANCE = (User32) Native.load("user32", User32.class);

    public static void main(String[] args) throws InterruptedException {

        HWND SkypeWindow = INSTANCE.FindWindowA(null, "Skype");

        SetInterval(j -> {
            try {
                RunSkype(j);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 2000, SkypeWindow);

    }

    public static void RunSkype(HWND SkypeWindow) throws InterruptedException {

        INSTANCE.SetForegroundWindow(SkypeWindow);

        INSTANCE.ShowWindow(SkypeWindow, WinUser.SW_RESTORE);

        Thread.sleep(1000);

        INSTANCE.ShowWindow(SkypeWindow, WinUser.SW_MINIMIZE);
    }

    public static <T> void SetInterval(Consumer<T> Method, int Time, T Value) throws InterruptedException {

        Thread.sleep(Time);

        Method.accept(Value);

        SetInterval(Method, Time, Value);

    }

}
