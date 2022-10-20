import { useRef } from 'react';

const useDebounce = (func: (...args: unknown[]) => void, delay: number) => {
	const timerId = useRef<number>();

	return (...args: unknown[]) => {
		if (timerId.current) {
			window.clearTimeout(timerId.current);
		}

		timerId.current = window.setTimeout(func, delay, args);
	};
};

export default useDebounce;
