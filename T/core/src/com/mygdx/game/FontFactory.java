package com.mygdx.game;

import java.util.Locale;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class FontFactory {

    // Font names
    public static final String SPANISH_FONT_NAME = "font/goodfish rg.ttf";
    public static final String RUSSIAN_FONT_NAME = "font/Furore.otf";

    // Russian cyrillic characters
    public static final String RUSSIAN_CHARACTERS = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"
            + "абвгдеёжзийклмнопрстуфхцчшщъыьэюя"
            + "1234567890.,:;_¡!¿?\"'+-*/()[]={}";

    // Singleton: unique instance
    private static FontFactory instance;

    private BitmapFont esFont;
    private BitmapFont ruFont;
    public static BitmapFont font;
    private static FreeTypeFontParameter parameter;
    private static FreeTypeFontGenerator generator;

    /** Private constructor for singleton pattern */
    private FontFactory() { super(); }

    public static synchronized FontFactory getInstance() {
        if (instance == null) {
            instance = new FontFactory();
        }
        return instance;
    }

    public void initialize() {
        if (esFont != null) esFont.dispose();
        if (ruFont != null) ruFont.dispose();

        esFont = generateFont(SPANISH_FONT_NAME, FreeTypeFontGenerator.DEFAULT_CHARS);
        ruFont = generateFont(RUSSIAN_FONT_NAME, RUSSIAN_CHARACTERS);
    }

    private BitmapFont generateFont(String fontName, String characters) {

        parameter = new FreeTypeFontParameter();
        parameter.characters = characters;
        parameter.size = 24;

        String path = Preffics.getInstance().getPath() + fontName;

        generator = new FreeTypeFontGenerator( Gdx.files.internal(path) );
        font = generator.generateFont(parameter);
        generator.dispose();
        return font;
    }


    /**
     * Get font for param locale
     *
     * @param locale  locale
     * @return BitmapFont
     */
    public BitmapFont getFont(Locale locale) {
        if      ("es".equals(locale.getLanguage())) return esFont;
        else if ("ru".equals(locale.getLanguage())) return ruFont;
        else throw new IllegalArgumentException("Not supported language");
    }

    /**
     * Dispose resources
     */
    public void dispose() {
        esFont.dispose();
        ruFont.dispose();
    }
}