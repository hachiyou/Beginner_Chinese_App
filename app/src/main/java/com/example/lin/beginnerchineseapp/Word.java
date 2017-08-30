package com.example.lin.beginnerchineseapp;

/**
 * Created by Lin on 08/07/2017.
 */

public class Word {
    /**
     * Constant value that represents no file was provided for this word
     */
    private static final int NO_FILE_PROVIDED = -1;
    /**
     * Chinese translation of the word
     **/
    private String myChineseTranslation;
    /**
     * Default translation of the word
     **/
    private String myDefaulTranslation;
    /**
     * Image resource id for the word
     **/
    private int imageId = NO_FILE_PROVIDED;
    /**
     * Audio resource for the word
     **/
    private int audioId = NO_FILE_PROVIDED;

    /**
     * Default constructor for the Word class.
     *
     * @param defaultTranslation the default translation for the created word
     * @param chineseTranslation the chinese translation for the created word
     * @param imageResource      is the image resource id
     * @param audioResource      is the audio resource id
     */
    public Word(String defaultTranslation, String chineseTranslation, int imageResource, int audioResource) {
        myChineseTranslation = chineseTranslation;
        myDefaulTranslation = defaultTranslation;
        imageId = imageResource;
        audioId = audioResource;
    }

    /**
     * Create a word with no audio or image
     **/
    public static Word createWord(String defaultTranslation, String chineseTranslation) {
        return new Word(defaultTranslation, chineseTranslation, NO_FILE_PROVIDED, NO_FILE_PROVIDED);
    }

    /**
     * Create a word with audio but no image
     **/
    public static Word createWordWithAudio(String defaultTranslation, String chineseTranslation, int audioResource) {
        return new Word(defaultTranslation, chineseTranslation, NO_FILE_PROVIDED, audioResource);
    }

    /**
     * Create a word with image but no audio
     **/
    public static Word createWordWithImage(String defaultTranslation, String chineseTranslation, int imageResource) {
        return new Word(defaultTranslation, chineseTranslation, imageResource, NO_FILE_PROVIDED);
    }

    /**
     * Obtain the chinese translation for the word.
     *
     * @return the chinese translation of this word as a String.
     */
    public String getChineseTranslation() {
        return myChineseTranslation;
    }

    /**
     * Obtain the default translation for the word.
     *
     * @return the default translation of this word as a String.
     */
    public String getDefaultTranslation() {
        return myDefaulTranslation;
    }

    /**
     * Obtain the audio resource id.
     *
     * @return the audio resource id as an integer
     */
    public int getAudioResourceID() {
        return audioId;
    }

    /**
     * Obtain the image resource id.
     *
     * @return the image resource id as an integer
     */
    public int getImageResourceID() {
        return imageId;
    }

    /**
     * Returns whether or not there is an image for this word.
     */
    public boolean hasImage() {
        return imageId != NO_FILE_PROVIDED;
    }

    /**
     * Returns whether or not there is an audio file for this word.
     */
    public boolean hasAudio() {
        return audioId != NO_FILE_PROVIDED;
    }
}
