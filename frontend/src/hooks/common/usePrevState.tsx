import { useEffect, useRef } from 'react';

const usePrevState = <T,>(state: T) => {
	const ref = useRef<T>(state);

	useEffect(() => {
		ref.current = state;
	}, [state]);

	return ref.current;
};

export default usePrevState;
