# Lucky Club Android starter

Profile: **MD Farid**. The Deposit page displays the supplied QR image.

This is a UI prototype, not a live wallet/payment app and not affiliated with BDG Win. It does not verify whether a payment has arrived. The QR image is shown for scanning only.

## Run
1. Extract `LuckyClubAndroid.zip`.
2. Install Android Studio (stable release).
3. Choose **Open** and select the `LuckyClubAndroid` folder containing `settings.gradle.kts`.
4. Let Gradle sync. Install Android SDK Platform 35 if prompted.
5. Start an emulator from **Tools > Device Manager**, or connect an Android phone with USB debugging enabled.
6. Press **Run ▶**.
7. To build an APK: **Build > Build Bundle(s) / APK(s) > Build APK(s)**.

## Important payment note
The QR image does not confirm incoming payment to the app. Do not trust screenshots or a user tapping “I have paid.” A live deposit ledger requires a compliant payment provider and server-side verification. Never ask users for UPI PINs, OTPs, or bank passwords.
