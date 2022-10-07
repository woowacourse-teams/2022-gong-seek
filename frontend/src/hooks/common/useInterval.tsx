import { useEffect, useRef } from 'react';

const useInterval = (callback: (...args: unknown[]) => void, delay: number) => {
	const savedCallback = useRef<(...args: unknown[]) => void>();

	useEffect(() => {
		savedCallback.current = callback;
	}, [callback]);

	useEffect(() => {
		function tick() {
			if (savedCallback.current) {
				savedCallback.current();
			}
		}
		if (delay !== null) {
			const id = setInterval(tick, delay);
			return () => clearInterval(id);
		}
	}, [delay]);
};

export default useInterval;
