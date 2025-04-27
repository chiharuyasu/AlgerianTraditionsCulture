# Algerian Traditions & Culture App

A mobile application (Android) that explores and celebrates Algeria's rich traditions, culture, and heritage. The app presents categories such as Food & Cuisine, Festivals & Celebrations, Marriage Customs, Historical Landmarks, and more. Each tradition includes descriptions, images, and optional audio or video content for a multimedia experience.

## Features
- Browse Algerian traditions by category
- View detailed descriptions, historical backgrounds, and related traditions
- High-quality images for each tradition
- Video and audio playback support for select traditions (add your own media to `res/raw`)
- Search functionality for quick access to traditions
- Modern, material-inspired UI

## Getting Started

### Prerequisites
- Android Studio (latest recommended)
- Android device or emulator (API 21+)

### Setup
1. **Clone or Download the Repository**
2. **Open in Android Studio**
3. **Build the Project**
   - The project uses Gradle. Click "Build" or run `./gradlew assembleDebug` in the project root.
4. **Run the App**
   - Deploy to an emulator or physical device.

### Adding Your Own Media
- To add a video or audio for a tradition, place your `.mp4` or `.mp3` file in `app/src/main/res/raw/`.
- Update the corresponding `videoUrl` or `audioUrl` in `CategoryDetailActivity.java` to match the file name (without extension).

## Project Structure
- `app/src/main/java/com/example/algeriantraditionsculture/` — Main source code
- `app/src/main/res/drawable/` — Images for traditions and categories
- `app/src/main/res/raw/` — Audio and video files
- `app/src/main/res/layout/` — XML layout files

## Contributing
Pull requests are welcome! For major changes, please open an issue first to discuss what you would like to change.

## License
This project is for educational and cultural purposes. For other uses, please contact the author.

---

### Contact
For questions or suggestions, please open an issue or contact the project maintainer.
