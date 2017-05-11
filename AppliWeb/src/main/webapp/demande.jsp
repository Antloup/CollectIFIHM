<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:basic_layout title="Accueil" nav_demander="active" nav_disconnected="hide">

    <jsp:attribute name="head_area">
    </jsp:attribute>
    <jsp:attribute name="body_area">
        <div class="row center-align">
            <h2>Poster une demande</h2>
        </div>
        <div class="row valign-wrapper">
            <div class="col s6 right-align">
                <h4>Activite :</h4>
            </div>
            <div class="col s6 left-align">
                <select id="select-activite">
                    <option value="" disabled selected>Chargement...</option>
                </select>
                <label></label>
            </div>
        </div>


        <div class="row valign-wrapper">
            <div class="col s6 right-align">
                <h4>Date :</h4>
            </div>
            <div class="col s6 left-align">
                <input id="date" type="date" class="datepicker">
            </div>
        </div>


        <div class="row valign-wrapper">
            <div class="col s6 right-align">
                <h4>Moment :</h4>
            </div>
            <div class="col s6 left-align">
                <select id="select-moment">
                    <option value="" disabled selected>Chargement...</option>
                </select>
                <label></label>
            </div>
        </div>


        <div class="row center-align">
            <button class="btn waves-effect waves-light" id="demande">Envoyer la demande</button>
        </div>

        <script>
            $(document).ready(function () {
                $('select').material_select();
                $('.datepicker').pickadate({
                    selectMonths: true, // Creates a dropdown to control month
                    selectYears: 15, // Creates a dropdown of 15 years to control year
                    firstDay: 1
                });
            });
        </script>
        
        <script>
            $("#demande").click(function() {
                console.log("Envoi d'une demande");
                console.log( $("#select-activite").val());
                console.log( $("#select-moment").val());
                console.log( $("#date").val());
                $.ajax({
                    url: "./ActionServlet",
                    type: "POST",
                    data: {
                        action : "insertDemande",
                        //id: window.location.search.substring(1), // URL.html?<id>
                        activite: $("#select-activite").val(),
                        moment: $("#select-moment").val(),
                        date: $("#date").val()
                    },
                    dataType: "json"
                })
                .done(function(data) {
                    console.log(data);
                    if(data.Demande == "OK"){
                        console.log("Inscription OK")
                        window.location.href = "accueil.jsp";
                    }
                    else{
                        console.log("Erreur Demande")
                        Materialize.toast('Erreur lors de la demande', 4000);
                    }
                    // Si c'est ok : Redirection Page accueil
                })
                .fail(function() {
                    console.log("fail");
                })
                .always(function() {
                    console.log("always");
                });
            });
        </script>

        <script>
            $(document).ready(function () {
                // getActivites
                $.ajax({
                    url: "./ActionServlet",
                    type: "POST",
                    data: {
                        action: "getActivites"
                    },
                    dataType: "json",
                    success: function (data) {
                        getMoment();
                    }
                })
                        .done(function (data) {
                            console.log(data);
                            var activites = data.activites;
                            console.log(activites);
                            var select_activite = $("#select-activite");
                            select_activite.empty();
                            $.each(activites, function (i, activite) {
                                select_activite.append("<option value=\"" + activite.id + "\">" + activite.denomination + "</option>");
                            })
                            if (activites.length == 0) {
                                select_activite.append("<option value=\"\" disabled selected>Pas d'activitée</option>");
                            }
                            $('select').material_select();
                            console.log("done");
                        })
                        .fail(function () {
                            console.log("fail");
                        })
                        .always(function () {
                            console.log("always");
                        });
            });
        </script>

        <script>
            function getMoment(){
                //getMoments
                $.ajax({
                    url: "./ActionServlet",
                    type: "POST",
                    data: {
                        action: "getMoments"
                    },
                    dataType: "json"
                })
                        .done(function (data) {

                            console.log(data);
                            var moments = data.moments;
                            console.log(moments);
                            var select_moment = $("#select-moment");
                            select_moment.empty();
                            $.each(moments, function (i, moment) {
                                select_moment.append("<option value=\"" + moment.id + "\">" + moment.denomination + "</option");
                            })
                            if (moments.length == 0) {
                                select_moment.append("<option value=\"\" disabled selected>Pas de moment</option>");
                            }
                            $('select').material_select();
                            console.log("done");

                        })
                        .fail(function () {
                            console.log("fail");
                        })
                        .always(function () {
                            console.log("always");
                        });
            };
        </script>
    </jsp:attribute>

</t:basic_layout>