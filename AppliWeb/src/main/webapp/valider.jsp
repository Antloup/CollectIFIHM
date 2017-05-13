<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:basic_layout title="Responsable" nav_connected="hide" nav_disconnected="hide">

    <jsp:attribute name="head_area">
    </jsp:attribute>
    <jsp:attribute name="body_area">
        <div class="row center-align">
            <h2>Valider un événement :</h2>
        </div>
        <div class="row">
            <div class="col s6">
                <div class="row">
                    <h4 class="center-align" id="denomination">Infos</h4>
                </div>
                <div class="row">
                    <p class="center-align" id="date">Date</p>
                </div>
                <div class="row">
                    <p class="center-align" id="moment">Moment</p>
                </div>
                <div class="row" id="payant">
                    <div class="col s6"><p class="right-align">Fixer un prix :</p></div>
                    <div class="col s6">
                        <input placeholder="0" value="0" id="prix" type="number" class="validate" required>
                        <label for="prix"></label>
                    </div>

                </div>
            </div>

            <div class="col s6">
                <div class="row">
                    <div id="map" style="width: 100%; height: 500px; margin-left: auto; margin-right: auto; position: relative; overflow: hidden;"></div>
                </div>
                <div class="row">
                    <div class="row">
                        <div class="col s2 right-align"><img class="responsive-img" src="http://maps.google.com/mapfiles/ms/icons/red-dot.png"></div>
                        <div class="col s10 left-align"><p>Adhérent</p></div>
                    </div>

                    <div class="row">
                        <div class="col s2 right-align"><img class="responsive-img" src="http://maps.google.com/mapfiles/ms/icons/green-dot.png"></div>
                        <div class="col s10 left-align"><p>Lieux</p></div>
                    </div>

                    <div class="row">
                        <div class="col s2 right-align"><img class="responsive-img" src="http://maps.google.com/mapfiles/ms/icons/blue-dot.png"></div>
                        <div class="col s4 left-align"><p>Lieu actif :</p></div>
                        <div class="col s6 left-align">
                            <select id="select-lieux" onchange="changeMarker();">
                                <option value="-1" disabled selected>Chargement...</option>
                            </select>
                            <label></label>
                        </div>

                    </div>

                </div>
            </div>
        </div>
        <div class="row">
            <div class="row center-align">
                <button class="btn waves-effect waves-light" id="valider" onclick="valider();">Valider l'évènement</button>
            </div>
        </div>

        <script>
            $(document).ready(function () {
                $('select').material_select();
                var id = getUrlParameter('id');
                console.log(id);
                $.ajax({
                    url: "./ActionServlet",
                    type: "POST",
                    data: {
                        action: "getListeAdmin",
                        id: id
                    },
                    dataType: "json",
                    success: function (data) {
                        getLieux();
                    }
                })
                        .done(function (data) {
                            console.log(data);
                            var activite = data.activites[0];
                            if (!activite.payant) { // On enlève le prix
                                $("#payant").addClass("hide");
                                $("#payant").val(-1);
                            }
                            $("#denomination").html(activite.denomination);
                            $("#date").html(activite.date);
                            $("#moment").html(activite.moment);
                        })
                        .fail(function () {
                            $("#nom").html("ERREUR");
                            console.log("fail");
                        })
                        .always(function () {
                            console.log("always");
                        });
            });
        </script>

        <script>
            function getLieux() {
                $lieuxMarkers = [];
                $.ajax({
                    url: "./ActionServlet",
                    type: "POST",
                    data: {
                        action: "getListeLieux"
                    },
                    dataType: "json",
                    success: function (data) {
                        getAdherent();
                    }
                })
                        .done(function (data) {
                            console.log(data);
                            var lieux = data.lieux;
                            console.log(lieux);
                            var select_lieux = $("#select-lieux");
                            select_lieux.empty();
                            $.each(lieux, function (i, lieu) {
                                select_lieux.append("<option value=\"" + lieu.id + "\">" + lieu.denomination + "</option>");
                                //Ajout marker
                                var myLatLng = {lat: lieu.lat, lng: lieu.lng};
                                var marker = new google.maps.Marker({
                                    position: myLatLng,
                                    map: map,
                                    title: lieu.denomination,
                                    icon: 'http://maps.google.com/mapfiles/ms/icons/green-dot.png'
                                });
                                marker.addListener('click', function () {
                                    selectMarker(marker.title, true);
                                });

                                $lieuxMarkers.push(marker);
                            })
                            console.log($lieuxMarkers);
                            if (lieux.length == 0) {
                                select_lieux.append("<option value=\"\" disabled selected>Pas de lieux</option>");
                            }
                            $('select').material_select();
                        })
                        .fail(function () {
                            $("#nom").html("ERREUR");
                            console.log("fail");
                        })
                        .always(function () {
                            console.log("always");
                        });

            }
        </script>

        <script>
            function getAdherent() {
                var id = getUrlParameter("id");
                $.ajax({
                    url: "./ActionServlet",
                    type: "POST",
                    data: {
                        action: "getListeAdherentsEvenement",
                        id: id
                    },
                    dataType: "json"
                })
                        .done(function (data) {
                            console.log(data);
                            var adherents = data.adherents;
                            console.log(adherents);
                            $.each(adherents, function (i, ad) {
                                //Ajout marker
                                var myLatLng = {lat: ad.latitude, lng: ad.longitude};
                                var marker = new google.maps.Marker({
                                    position: myLatLng,
                                    map: map,
                                    title: ad.nom,
                                    icon: 'http://maps.google.com/mapfiles/ms/icons/red-dot.png'
                                });
                            })
                        })
                        .fail(function () {
                            $("#nom").html("ERREUR");
                            console.log("fail");
                        })
                        .always(function () {
                            console.log("always");
                        });
            }
        </script>

        <script>
            function selectMarker(nom, refresh) {
                $.each($lieuxMarkers, function (i, marker) {
                    marker.setIcon('http://maps.google.com/mapfiles/ms/icons/green-dot.png');
                    if (marker.title == nom) {
                        marker.setIcon('http://maps.google.com/mapfiles/ms/icons/blue-dot.png');

                        if (refresh) { // On doit refresh le select
                            $("#select-lieux option").each(function () // Pour chaque options
                            {
                                //var id = $("#select-lieux").val(); // Recuperation de l'id
                                var id = $(this).val();
                                var lieu = $("#select-lieux option[value='" + id + "']").text();
                                if (lieu == marker.title) { // Mettre a jour le select
                                    //$("#select-lieux[id$='"+id+"']").attr("selected", true);
                                    $("#select-lieux").val(id);
                                    $('#select-lieux').material_select();
                                }
                                //console.log($(this).val());
                            });
                        }

                    }
                })
            }
        </script>

        <script>
            function changeMarker() {
                var lieu = $("#select-lieux option:selected").text();
                console.log(lieu);
                selectMarker(lieu, false);

            }
        </script>

        <script>
            function valider() {
                var prix = $("#prix").val();
                var lieu = $("#select-lieux").val();
                var id = getUrlParameter("id");
                console.log(prix);
                console.log(lieu);
                console.log(id);

                $.ajax({
                    url: "./ActionServlet",
                    type: "POST",
                    data: {
                        action: "valider",
                        id: id,
                        prix: prix,
                        lieu: lieu
                    },
                    dataType: "json"
                })
                        .done(function (data) {
                            if (data.Validation == "OK") {
                                console.log("Validation OK");
                                window.location.href = "admin.jsp";
                            } else {
                                console.log("Erreur Validation");
                                Materialize.toast('Erreur Validation', 4000);
                            }
                        })
                        .fail(function () {
                            $("#nom").html("ERREUR");
                            console.log("fail");
                        })
                        .always(function () {
                            console.log("always");
                        });
            }
        </script>

        <script>
            var map;
            function initMap() {
                map = new google.maps.Map(document.getElementById('map'), {
                    center: {lat: 45.782938, lng: 4.878273},
                    zoom: 13
                });
            }
        </script>


        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAhf3JleYpal9S-xouJYH8lf7Dvz5Y2Nko&callback=initMap"
        async defer></script>


    </jsp:attribute>

</t:basic_layout>