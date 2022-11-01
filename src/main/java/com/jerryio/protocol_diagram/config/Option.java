package com.jerryio.protocol_diagram.config;

import com.jerryio.protocol_diagram.command.HandleResult;
import com.jerryio.protocol_diagram.token.Parameter;

/**
 * this abstract class provides a basic shape of an option class, that takes a key
 * in order to instantiate an option class, once it is set, it could not be changed
 * afterward
 */
public abstract class Option {

    /**
     * the key of this option, it should be treated as a readonly value,
     * since by logic, the key of a option should not be able to be changed,
     * otherwise it would cause confusions
     */
    private String key;

    /**
     * option constructor that sets the value of the key of this option, once it set,
     * it will be come readonly afterward
     * 
     * @param key
     */
    public Option(String key) {
        this.key = key;
    }

    /**
     * a getter method that retrieves the readonly key
     * 
     * @return String
     */
    public String getKey() {
        return key;
    }

    /**
     * a setter method that sets the value of this option
     * 
     * @param value
     * @return HandleResult
     */
    public abstract HandleResult setValue(Parameter value);

    /**
     * a getter method that retrieves the value of this option
     * 
     * @return Object
     */
    public abstract Object getValue();

    /**
     * a getter method that retrieves the default value of this option
     * 
     * @return Object
     */
    public abstract Object getDefault();

    /**
     * a method that returns a manual statement for displaying the usage description
     * 
     * @return String
     */
    public abstract String getUsageDescription();

}
