package me.shamanov.resumebd.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Author: Mike
 * Date: 30.03.2019
 *
 * This class represents a resume with different fields and sections to form a description of professional skills, experience
 * and education of a concrete person. Each resume has its on unique id (randomly assigned on instantiation) to be saved within a storage representative
 * e.g. database, filesystem etc.
 */


public final class Resume implements Comparable<Resume>, Serializable {
    private static final long serialVersionUID = 1L;

    private final String id;
    private String fullName;
    private String location;
    private String homepage;
    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private final Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);

    private Resume(String fullName, String location, String homepage) {
        Objects.requireNonNull(fullName, "Full name must not be empty!");
        Objects.requireNonNull(location, "Location must not be empty!");
        Objects.requireNonNull(homepage, "Homepage must not be empty!");

        this.id = generateRandomId();
        this.fullName = fullName;
        this.location = location;
        this.homepage = homepage;
    }

    /**
     * Creates a new instance of a {@code Resume} where all the fields must be specified.
     * Although you have to manually add contacts and sections after an instance will be created.
     *
     * @return {@code Resume} with full name, location and homepage specified.
     */
    public static Resume of(String fullName, String location, String homepage) {
        return new Resume(fullName, location, homepage);
    }

    /**
     * Creates a new instance of a {@code Resume} where only a full name of a person should be specified,
     * other {@code String} fields like location and homepage are being replaced with empty values by quoting:
     * <p><code>String example = "";</code></p>
     *
     * @return a new instance of {@code Resume} with specified full name of a person, other fields remain empty.
     */
    public static Resume of(String fullName) {
        return new Resume(fullName, "", "");
    }

    /**
     * Returns a randomly generated unique id to be used for {@code Resume} instances.
     * @return id generated by static {@link UUID#randomUUID} method.
     */
    private static String generateRandomId() {
        return UUID.randomUUID().toString();
    }

    /**
     * Indicates whether other Resume object is "equal to" this one using all the fields used to describe a concrete person.
     * @param o the reference object with which to compare.
     * @return true if equals and false if not.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return  Objects.equals(id, resume.id) &&
                Objects.equals(fullName, resume.fullName) &&
                Objects.equals(location, resume.location) &&
                Objects.equals(homepage, resume.homepage) &&
                Objects.equals(contacts, resume.contacts) &&
                Objects.equals(sections, resume.sections);
    }

    /**
     * @return hashcode upon unique id which is randomly generated on instantiation
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }

    /**
     * @return unique id for this concrete {@code Resume} instance.
     */
    public String getId() {
        return id;
    }

    /**
     * @return full name (first name and last name concatenated) for this concrete {@code Resume} instance.
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @return location (place of living) for this concrete {@code Resume} instance.
     */
    public String getLocation() {
        return location;
    }

    /**
     * @return homepage link for this concrete {@code Resume} instance.
     */
    public String getHomepage() {
        return homepage;
    }

    /**
     * Returns a <b>copy</b> of contacts that are represented by {@link EnumMap}
     * @return {@code Map} which represents the groups of {@code ContactType} and {@code String} of a person.
     */
    public Map<ContactType, String> getContacts() {
        return new EnumMap<>(contacts);
    }

    /**
     * Returns a <b>copy</b> of sections that are represented by {@link EnumMap}
     * @return {@code Map} which represents the groups of {@code SectionType} and {@code Section} of a person.
     */
    public Map<SectionType, Section> getSections() {
        return new EnumMap<>(sections);
    }

    /**
     * @param fullName first name and last name of a person specified for this concrete {@code Resume} instance. 
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @param location place of living of a person specified for this concrete {@code Resume} instance. 
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @param homepage link to a person's homepage specified for this concrete {@code Resume} instance. 
     */
    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    /**
     * @param contactType type of a contact to be added, go to {@link ContactType} to see a list of available types.  
     * @param value a concrete value for this type, e.g. mobile phone: "+7 999 123 44 55".
     * @return a previous {@code String} value for specified contactType.
     */
    public String addContact(ContactType contactType, String value) {
        return contacts.put(contactType, value);
    }

    /**
     * @param sectionType type of a section to be added, go to {@link SectionType} to see a list of available types.  
     * @param section a concrete representation of {@code Section} for this type.
     * @return a previous {@code Section} value for specified sectionType.
     */
    public Section addSection(SectionType sectionType, Section section) {
        return sections.put(sectionType, section);
    }

    private void writeObject(ObjectOutputStream os) throws IOException {
        os.defaultWriteObject();
    }

    private void readObject(ObjectInputStream os) throws IOException, ClassNotFoundException {
        os.defaultReadObject();
    }

    /**
     * Compares two Resume objects by person's full name of each, and if they equal it compares their unique IDs.
     * @param o the reference object with which to compare.
     * @return 0 if fullName.compareTo(o.fullName) returns 0, -1 or 1 if id.compareTo(o.id) returns any (in theory, it may return 0 as well).
     */
    @Override
    public int compareTo(Resume o) {
        int result = fullName.compareTo(o.fullName);
        return result == 0 ? id.compareTo(o.id) : result;
    }
}
