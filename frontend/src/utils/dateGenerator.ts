export const todayGenerator = () => {
	const date = new Date();
	date.setDate(date.getDate());
	const year = date.getFullYear();
	const month = date.getMonth() + 1;
	const day = date.getDate();

	return `${year}-${String(month).padStart(2, '0')}-${String(day).padStart(2, '0')}`;
};

export const afterWeekGenerator = () => {
	const date = new Date();
	date.setDate(date.getDate() + 6);

	const year = date.getFullYear();
	const month = date.getMonth() + 1;
	const day = date.getDate();

	return `${year}-${String(month).padStart(2, '0')}-${String(day).padStart(2, '0')}`;
};

export const currentTimeGenerator = () => {
	const date = new Date();
	date.setDate(date.getDate());
	const hour = date.getHours();

	return `${String(hour + 1).padStart(2, '0')}:00`;
};
