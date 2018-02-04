package com.github.messenger4j.internal.gson;

import com.github.messenger4j.common.SupportedCountry;
import com.github.messenger4j.messengerprofile.targetaudience.BlacklistTargetAudience;
import com.github.messenger4j.messengerprofile.targetaudience.TargetAudience;
import com.github.messenger4j.messengerprofile.targetaudience.WhitelistTargetAudience;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
final class TargetAudienceSerializer implements JsonSerializer<TargetAudience> {

    @Override
    public JsonElement serialize(TargetAudience targetAudience, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject targetAudienceObject = new JsonObject();
        targetAudienceObject.add("audience_type", context.serialize(targetAudience.audienceType()));

        boolean isWhitelistAudience = targetAudience instanceof WhitelistTargetAudience;
        boolean isBlacklistAudience = targetAudience instanceof BlacklistTargetAudience;

        if (isWhitelistAudience || isBlacklistAudience) {
            final JsonObject countriesObject = new JsonObject();
            if (isWhitelistAudience) {
                countriesObject.add("whitelist", context.serialize(transformCountries(
                        ((WhitelistTargetAudience) targetAudience).countries())));
            }
            if (isBlacklistAudience) {
                countriesObject.add("blacklist", context.serialize(transformCountries(
                        ((BlacklistTargetAudience) targetAudience).countries())));
            }
            targetAudienceObject.add("countries", countriesObject);
        }

        return targetAudienceObject;
    }

    private List<String> transformCountries(List<SupportedCountry> countries) {
        return countries.stream().map(Enum::name).collect(Collectors.toList());
    }
}
