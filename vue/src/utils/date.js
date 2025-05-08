export function formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleDateString(); // or any other formatting you prefer
    // Alternatively, use a library like date-fns or moment.js for more complex formatting
}