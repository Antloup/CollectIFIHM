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
                    <h4 class="center-align">Infos</h4>
                </div>
                <div class="row">
                    <div class="col s6"><p class="right-align">Fixer un prix :</p></div>
                    <div class="col s6">
                        <input placeholder="10" id="prix" type="number" class="validate">
                        <label for="prix"></label>
                    </div>

                </div>
            </div>

            <div class="col s6">
                <div class="row">
                    <div id="map" style="width: 100%; height: 500px; margin-left: auto; margin-right: auto; position: relative; overflow: hidden;"></div>
                </div>
                <div class="row">
                    <p>Adhérent</p>
                    <p>Lieux</p>
                    <div class="row">
                        <div class="col s3 left-align"><p>Lieu actif :</p></div>
                        <div class="col s9 left-align">
                            <select id="select-activite">
                                <option value="" disabled selected>Chargement...</option>
                                <option value="" >TODO</option>
                            </select>
                            <label></label>
                        </div>

                    </div>

                </div>
            </div>
        </div>
        <div class="row">
            <div class="row center-align">
                <button class="btn waves-effect waves-light" id="valider">Valider l'évènement</button>
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
                    dataType: "json"
                })
                        .done(function (data) {
                            console.log(data);
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