import { useEffect, useRef } from 'react';

import useInterval from '@/hooks/common/useInterval';

const useScroll = (callback: (...args: unknown[]) => void) => {
	const isScroll = useRef(false);

	const handleSetHeaderShow = () => {
		isScroll.current = true;
	};

	useEffect(() => {
		window.addEventListener('scroll', handleSetHeaderShow);

		return () => {
			window.removeEventListener('scroll', handleSetHeaderShow);
		};
	}, []);

	useInterval(() => {
		if (isScroll) {
			callback();
			isScroll.current = false;
		}
	}, 10);
};

export default useScroll;
