document.querySelectorAll('form[action="/search"]').forEach(function(form) {
    form.addEventListener('submit', function(e) {
        var input = this.querySelector('input[name="q"]');
        if (input && !input.value.trim()) e.preventDefault();
    });
});
