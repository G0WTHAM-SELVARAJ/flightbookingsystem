/**
 * Copyright (C) 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package conf;


import controllers.AuthController;
import controllers.FlightDataController;
import controllers.UserDataController;
import ninja.AssetsController;
import ninja.Router;
import ninja.application.ApplicationRoutes;
import controllers.ApplicationController;

public class Routes implements ApplicationRoutes {

    @Override
    public void init(Router router) {  
        
        router.GET().route("/").with(ApplicationController::index);
        router.GET().route("/hello_world.json").with(ApplicationController::helloWorldJson);
		router.POST().route("/auth/verify").with(AuthController::authVerify);

        router.POST().route("/api/flights").with(FlightDataController::addFlightDetail);
        router.GET().route("/api/flights").with(FlightDataController::retrieveAllFlightDetails);
        router.PUT().route("/api/flights").with(FlightDataController::updateFlightDetail);
        router.DELETE().route("/api/flights").with(FlightDataController::deleteFlightDetail);
        router.GET().route("/api/flights/search").with(FlightDataController::searchByParams);
        router.POST().route("/api/flights/bookings/confirm").with(FlightDataController::confirmBooking);
        router.POST().route("/api/flights/bookings/cancel").with(FlightDataController::cancelBooking);
        router.GET().route("/api/flights/{flightId}").with(FlightDataController::getCustomFlightDetail);

//        //user data controller
        router.GET().route("/api/users/bookings/history").with(UserDataController::getAllBookingHistory);
        router.GET().route("/api/users/bookings/history/{id}").with(UserDataController::getUserHistory);
        router.POST().route("/api/users/wallets/credit").with(UserDataController::creditUserWallet);
        router.POST().route("/api/users/wallets/debit").with(UserDataController::debitUserWallet);




        router.GET().route("/assets/webjars/{fileName: .*}").with(AssetsController::serveWebJars);
        router.GET().route("/assets/{fileName: .*}").with(AssetsController::serveStatic);
        
        ///////////////////////////////////////////////////////////////////////
        // Index / Catchall shows index page
        ///////////////////////////////////////////////////////////////////////
        router.GET().route("/.*").with(ApplicationController::index);
    }

}
