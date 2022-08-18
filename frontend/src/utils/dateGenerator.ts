export const tomorrowGenerator = () => {
	const date = new Date();
	date.setDate(date.getDate() + 1);
	const year = date.getFullYear();
	const month = date.getMonth() + 1;
	const day = date.getDate();

	return `${year}-${String(month).padStart(2, '0')}-${String(day).padStart(2, '0')}`;
};

export const afterWeekGenerator = () => {
	const date = new Date();
	date.setDate(date.getDate() + 7);

	const year = date.getFullYear();
	const month = date.getMonth() + 1;
	const day = date.getDate();

	return `${year}-${String(month).padStart(2, '0')}-${String(day).padStart(2, '0')}`;
};
