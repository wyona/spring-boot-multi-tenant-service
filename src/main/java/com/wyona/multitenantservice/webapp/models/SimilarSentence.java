package com.wyona.multitenantservice.webapp.models;

/**
 *
 */
public class SimilarSentence {

    private String sentence;
    private double distance;

    /**
     *
     */
    public SimilarSentence() {
    }

    /**
     * @param sentence Sentence, e.g. "What is the best mountain bike in the price range of USD 2000?"
     * @param distance Distance re similarity, whereas 0.1 means sentence is very similar and 0.9 means sentence is not very similar
     */
    public SimilarSentence(String sentence, double distance) {
        this.sentence = sentence;
        this.distance = distance;
    }

    /**
     *
     */
    public String getSentence() {
        return sentence;
    }

    /**
     *
     */
    public void setSetence(String sentence) {
        this.sentence = sentence;
    }
}
