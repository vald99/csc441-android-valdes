# Campus App

This README has been created for compliance with Checkpoint 1, to maintain compliance with project requirements.
Lab App features a variety of tools. Only messages are implemented thus far.
Built by Yandel Valdes for CSC441.

## Screens
- **Home** - features text input for the messages feature, which messages the main list
- **List** - scrollable list to see all messages beyond the limited window of the home screen
- **About** - Basic synopsis of the app; same as what's featured above

## Input rules
| Input                  | What the app says                                   |
|------------------------|-----------------------------------------------------|
| (empty)                | Empty messages cannot be added                      |
| Message minimum length | Messages must be 3 or more characters               |
| Message maximum length | Messages are permitted to be a max of 30 characters |
| Duplicates             | Messages cannot be repeated                         |
| Leading character      | Messages must start with a letter                   |

### How to run
1. Clone this repo
2. Open the cloned folder in Android Studio
3. Wait for Gradle sync, then Run

## Known limitations
- **IMPORTANT:** Added messages disappear upon screen rotation
- The home screen currently does not feature any scrolling capability, so the only way to see all messages after a certain point is to go to the list screen
- Some buttons are still formatted incorrectly and may be more difficult to see
- There is some clipping with the top banner and the notification bar
 