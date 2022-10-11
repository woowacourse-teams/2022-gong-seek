import { VoteDateFormat } from 'gongseek-types';
import { Day, Month } from 'gongseek-types/lib/util';

export const todayGenerator = (): VoteDateFormat => {
	const date = new Date();
	date.setDate(date.getDate());
	const year = date.getFullYear();
	const month = date.getMonth() + 1;
	const day = date.getDate();

	const tMonth = String(month).padStart(2, '0') as Month;
	const tDay = String(day).padStart(2, '0') as Day;

	return `${year}-${tMonth}-${tDay}`;
};

export const afterWeekGenerator = (): VoteDateFormat => {
	const date = new Date();
	date.setDate(date.getDate() + 6);

	const year = date.getFullYear();
	const month = date.getMonth() + 1;
	const day = date.getDate();

	const tMonth = String(month).padStart(2, '0') as Month;
	const tDay = String(day).padStart(2, '0') as Day;

	return `${year}-${tMonth}-${tDay}`;
};

export const currentTimeGenerator = () => {
	const date = new Date();
	date.setDate(date.getDate());
	const hour = date.getHours();

	return `${String(hour + 1).padStart(2, '0')}:00`;
};
