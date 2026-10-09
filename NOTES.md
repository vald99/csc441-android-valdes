### Lab 5 Wednesday Questions
1. **How long did your first build take? And the second?** About 12 seconds, then 3 for the second build.
2. **In dark mode, what changed color on its own?** The background elements and the plain text message.
3. **What is one thing on your screen right now that you don't understand yet?** I still don't really understand what this gradle thing is besides the, "it's the build system, duh!" part.

### Week 5, Friday. 
*Change one Modifier on your Column — the padding number, or swap .fillMaxWidth() for .fillMaxSize(). Write down what you changed and what happened to the screen. One or two sentences.*
Changing the padding number from 24 to 20 made the text and header image get slightly closer to the borders of the screen. Swapping `.fillMaxWidth` for `.fillMaxSize` had no noticable effect on the text formatting.

### Week 6, Wednesday.
1. **Paste the logcat lines from your broken counter.**  18:39:22.546  E  FATAL EXCEPTION: main
2. **In your own words, why did count change but the screen didn't?** I believe the variable was changing in the background, but since the variable wasn't part of the screen state, the screen didn't know to update it, so it stayed as 0.
3. **What does remember do? What would happen without it?** Remember is adding the count variable to the state, and if we don't add it then the activity will only display the count variable as it was when it was initially copied.

### Week 6, Friday.
- The app should have a rule for starting messages with letters since each of the messages is supposed to give information, and preventing numeric or symbolic answers (at least for the first character)

| I typed                             | What the app did                             | Correct? |
|-------------------------------------|----------------------------------------------|----------|
| (nothing)                           | Add button greyed out                        | yes      |
| " " (spaces)                        | Add button greyed out                        | yes      |
| Too long                            | Couldn't type more                           | yes      |
| Yellow and yellow (different cases) | Error for the second yellow                  | yes      |
| !message                            | Error for non-alphabetical start character   | yes      |
| hello                               | Added to list (alphabetical start character) | yes      |
| ( ͡° ͜ʖ ͡°)                         | Failed to add (non-alpha start charcter)     | yes      |
| HELLOHELLOHELLOHELLOHELLOHELLO      | Added (couldn't type more hellos)            | yes      |

### Week 7, Wednesday.
1. **After rotating, which screen were you on?** After rotating, I was still on the list screen.
2. **Were your two new items still there?** No, they disappeared and the count went back to 3.
3. **Look at how currentScreen and welcomeMessages are created in CampusAppScreen. Explain the difference in one or two sentences.** Both currentScreen and welcomeMessages are created with the remember flags, but the currentScreen variable has a 'Saveable' portion added to it. I'm not entirely sure what it's doing for us, but I assume it has to do with it being able to keep the screen when rotating, while the list refreshes.