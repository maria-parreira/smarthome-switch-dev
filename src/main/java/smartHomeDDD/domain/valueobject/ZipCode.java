package smartHomeDDD.domain.valueobject;


import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * The ZipCode class represents a postal code for a specific country.
 * It provides methods for constructing a ZipCode object, validating the zip code,
 * and checking equality with other ZipCode objects.
 */
public class ZipCode {

    /**
     * The country value.
     */
    private final String _country;

    /**
     * The zip code value.
     */
    private final String _zipCode;

    /**
     * Constructs a ZipCode object with the given string representation of the zip code.
     * @param zipCode A string representing the zip code.
     * @param country A string representing the zip code.
     * @throws IllegalArgumentException If the provided stringZipCode is null, empty, or blank.
     */
    public ZipCode(String country,String zipCode){
        if(country.isEmpty() || zipCode.isEmpty() || !isZipCodeValid(country, zipCode)){
            throw new IllegalArgumentException("Invalid arguments provided.");
        }
        this._zipCode=zipCode;
        this._country=country;
    }

    /**
     * Checks if the provided zip code is valid for the given country.
     * @param country The country for which the zip code validity is to be checked.
     * @param zipCode The zip code to be validated.
     * @return {@code true} if the zip code is valid for the specified country, otherwise {@code false}.
     * @throws IllegalArgumentException if the country provided is not supported.
     */
    private boolean isZipCodeValid(String country,String zipCode) {
        String regex = switch (country) {
            case "Portugal" -> "\\d{4}-\\d{3}";
            case "France" -> "\\d{5}";
            case "USA" -> "\\d{5}-\\d{4}";
            case "Canada" -> "[A-Za-z]\\d[A-Za-z] \\d[A-Za-z]\\d";
            default -> throw new IllegalArgumentException("Invalid country");
        };
        return matchRegex(regex,zipCode);
    }

    /**
     * Matches the provided regex pattern against the given zip code.
     * @param regex   The regular expression pattern to match against.
     * @param zipCode The zip code to be matched against the regex pattern.
     * @return {@code true} if the zip code matches the regex pattern, otherwise {@code false}.
     */
    private boolean matchRegex(String regex,String zipCode){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(zipCode);
        return matcher.matches();
    }


    /**
     * Indicates whether some other object ZipCode is "equal to" this one.
     * @param object The reference object with which to compare.
     * @return True if this object is the same as the object argument; false otherwise.
     */
    @Override
    public boolean equals(Object object) {

        if (this == object)
            return true;

        if (object instanceof ZipCode zipCode) {

            return this._zipCode.equals(zipCode._zipCode);
        }
        return false;
    }

    /**
     * Retrieves the country associated with zip code.
     * @return The country as a String.
     */
    public String getCountry(){
        return this._country;
    }

    /**
     * Returns a string representation of the ZipCode object.
     * @return The zip code value as a string.
     */
    @Override
    public String toString() {
        return this._zipCode ;
    }

    /**
     * Returns the hash code value for this ZipCode object.
     * @return The hash code value for this ZipCode object.
     */
    @Override
    public int hashCode() {
        return _zipCode.hashCode() + _country.hashCode();
    }
}
