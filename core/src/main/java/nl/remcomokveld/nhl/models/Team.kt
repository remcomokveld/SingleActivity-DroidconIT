package nl.remcomokveld.nhl.models

import android.os.Parcel
import android.os.Parcelable

enum class Team(val humanName: String) : Parcelable {

    BOSTON_BRUIN("Boston Bruin"),
    BUFFALO_SABRE("Buffalo Sabre"),
    DETROIT_RED_WINGS("Detroit Red Wings"), //Detroit Red Wings
    FLORIDA_PANTHER("Florida Panther"),
    MONTREAL_CANADIEN("Montreal Canadien"),
    OTTAWA_SENATOR("Ottawa Senator"),
    TAMPA_BAY_LIGHTNIN("Tampa Bay Lightnin"),
    TORONTO_MAPLE_LEAFS("Toronto Maple Leafs"),

    CAROLINA_HURRICANES("Carolina Hurricanes"),
    COLUMBUS_BLUE_JACKETS("Columbus Blue Jackets"),
    NEW_JERSEY_DEVILS("New Jersey Devils"),
    NEW_YORK_ISLANDERS("New York Islanders"),
    NEW_YORK_RANGERS("New York Rangers"),
    PHILADELPHIA_FLYERS("Philadelphia Flyers"),
    PITTSBURGH_PENGUINS("Pittsburgh Penguins"),
    WASHINGTON_CAPITALS("Washington Capitals"),

    CHICAGO_BLACKHAWKS("Chicago Blackhawks"),
    COLORADO_AVALANCHE("Colorado Avalanche"),
    DALLAS_STARS("Dallas Stars"),
    MINNESOTA_WILD("Minnesota Wild"),
    NASHVILLE_PREDATORS("Nashville Predators"),
    ST_LOUIS_BLUES("St. Louis Blues"),
    WINNIPEG_JETS("Winnipeg Jets"),

    ANAHEIM_DUCKS("Anaheim Ducks"),
    ARIZONA_COYOTES("Arizona Coyotes"),
    CALGARY_FLAMES("Calgary Flames"),
    EDMONTON_OILERS("Edmonton Oilers"),
    LOS_ANGELES_KINGS("Los Angeles Kings"),
    SAN_JOSE_SHARKS("San Jose Sharks"),
    VANCOUVER_CANUCKS("Vancouver Canucks"),
    VEGAS_GOLDEN_KNIGHTS("Vegas Golden Knights");

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Team> {
        override fun createFromParcel(parcel: Parcel): Team =
            Team.valueOf(parcel.readString()!!)

        override fun newArray(size: Int): Array<Team?> = arrayOfNulls(size)
    }
}
