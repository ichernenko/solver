package dictionary;

import partsOfSpeech.*;


class WordProperty {
    private int lemmaId;
    private PartOfSpeech partOfSpeech;

    WordProperty(int lemmaId, String partOfSpeechString, String tag) {
        this.lemmaId = lemmaId;
        String[] grammemeArray = tag != null ? tag.split(" ") : new String[]{};

        switch(partOfSpeechString) {
            case "сущ"    : partOfSpeech = new Noun(grammemeArray); break;
            case "прил"   : partOfSpeech = new Adjective(grammemeArray); break;
            case "числ"   : partOfSpeech = new Numeral(grammemeArray); break;
            case "мест"   : partOfSpeech = new Pronoun(grammemeArray); break;
            case "гл"     : partOfSpeech = new Verb(grammemeArray); break;
            case "нар"    : partOfSpeech = new Adverb(grammemeArray); break;
            case "предик" : partOfSpeech = new PredicateNoun(grammemeArray); break;
            case "прич"   : partOfSpeech = new Participle(grammemeArray); break;
            case "дееп"   : partOfSpeech = new AdverbialParticiple(grammemeArray); break;
            case "предл"  : partOfSpeech = new Preposition(grammemeArray); break;
            case "союз"   : partOfSpeech = new Conjunction(grammemeArray); break;
            case "част"   : partOfSpeech = new Particle(grammemeArray); break;
            case "межд"   : partOfSpeech = new Interjection(grammemeArray); break;
            case "ввод"   : partOfSpeech = new ParentheticalWord(grammemeArray); break;
        }
    }

    public int getLemmaId() {
        return lemmaId;
    }
    public PartOfSpeech getPartOfSpeech() {
        return partOfSpeech;
    }
}