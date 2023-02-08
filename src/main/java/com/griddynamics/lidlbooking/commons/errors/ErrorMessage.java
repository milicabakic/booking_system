package com.griddynamics.lidlbooking.commons.errors;

public abstract class ErrorMessage {

    public static final String ADMIN_ERROR = "Admin: ";
    public static final String BOOKING_PROVIDER_ERROR = "BookingProvider: ";
    public static final String BOOKING_USER_ERROR = "BookingUser: ";

    public static final String USERNAME_FORMAT_EXCEPTION = "Username must be a String";
    public static final String ID_FORMAT_EXCEPTION = "ID must be a number";
    public static final String USERNAME_INVALID_EXCEPTION = "User with provided username doesn't exist";
    public static final String ID_INVALID_EXCEPTION = "User with provided ID doesn't exist";
    public static final String INVALID_CREDENTIALS = "Invalid credentials";
    public static final String ACCESS_DENIED = "Access denied";
    public static final String TOKEN_EXPIRED = "Token expired";

    public static final String AMENITY_ERROR = "Amenity: ";
    public static final String AMENITY_NULL = "Amenity must not be null";
    public static final String AMENITY_ID_INVALID_FORMAT = "Amenity Id must be a number";
    public static final String AMENITY_NAME_INVALID_FORMAT = "Amenity name must be a String";
    public static final String AMENITY_INVALID_PARENT = "Amenity parent must contain id";

    public static final String CITY_ID_INVALID = "City Id must be a number";
    public static final String CITY_NOT_IN_COUNTRY = "City isn't in this country";
    public static final String COUNTRY_ID_INVALID = "Country Id must be a String";

    public static final String SERVICE_FACILITY_ERROR = "Service facility: ";
    public static final String SERVICE_FACILITY_NULL = "Service facility must not be null";
    public static final String SERVICE_FACILITY_ID_INVALID_FORMAT = "Service facility Id must be a number";

    public static final String STUDIO_ERROR = "Studio: ";
    public static final String STUDIO_NULL = "Studio must not be null";
    public static final String STUDIO_ID_INVALID_FORMAT = "Studio Id must be a number";

    public static final String SEASON_ERROR = "Season: ";
    public static final String SEASON_NULL = "Season must not be null";
    public static final String SEASON_FORMAT = "Provided invalid format";
    public static final String SEASON_SAVE = "Season must not be null.";
    public static final String SEASON_VALIDATION = "Required permission to change requested season";
    public static final String CREATORS_MISMATCH = "Season creator and Studio creator does not match";
    public static final String SEASONS_OVERLAP = "Seasons are overlapping";
    public static final String SEASONS_INVALID = "Provided Invalid Seasons";
    public static final String SEASON_STUDIO_MISMATCH = "Provided Seasons do not match with a provided studio";

    public static final String BOOKING_ERROR = "Booking: ";
    public static final String BOOKING_STUDIO = "Studio has been already booked in the specified date range.";
    public static final String BOOKING_VALIDATION = "Studio must not be null";
    public static final String BOOKING_SEASON = "Studio can't be booked in provided date range.";


    public static String amenityIdDoesNotExist(final Long id) {
        return "Amenity with id=" + id + " doesn't exist";
    }

    public static String amenityNameDoesNotExist(final String name) {
        return "Amenity with name=" + name + " doesn't exist";
    }

    public static String cityIdDoesNotExist(final Long id) {
        return "City with id=" + id + " doesn't exist";
    }

    public static String countryIso3DoesNotExist(final String iso3) {
        return "Country with iso3=" + iso3 + "doesn't exist";
    }

    public static String serviceFacilityIdDoesNotExist(final Long id) {
        return "Service facility with id=" + id + " doesn't exist";
    }

    public static String studioIdDoesNotExist(final Long id) {
        return "Studio with id=" + id + " doesn't exist";
    }

    public static String formatVariable(final Object variable) {
        return "[ " + variable.toString() + " ]";
    }

    public static String seasonIdDoesNotExist(final Long id) {
        return "Season with ID = " + id + " doesn't exist";
    }
}
