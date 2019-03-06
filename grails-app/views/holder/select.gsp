<label for="pet-select" id="pet-result">Choose a pet:</label>

<%-- <input type="text" id="pet-select" required
      size="10">

</input>
--%>

<select id="pet-select" size="4" multiple="true" onchange="detectChange(event)">
    <option value="">--Please choose an option--</option>
    <option value="dog">Dog</option>
    <option value="cat">Cat</option>
    <option value="hamster">Hamster</option>
    <option value="parrot">Parrot</option>
    <option value="spider">Spider</option>
    <option value="goldfish">Goldfish</option>
</select>

<script>
    $(document).Ready (function() {

        function detectChange(ev) {
            //var pet = $(this).val();
            var pet = $('#pet-select').val();
            $('pet-result'.val(pet));
        };

        $('#pet-select').change(function () {
            var pet = $(this).val();
            $('pet-result'.val(pet));
        })

</script>