import { useEffect, useRef } from 'react';

const useEnterToClick = () => {
	const ref = useRef<HTMLDivElement | null>(null);

	useEffect(() => {
		const handleEnterKeydown = (e: KeyboardEvent) => {
			if (e.key === 'Enter') {
				(e.target as HTMLElement).click();
			}
		};

		if (ref.current) {
			ref.current.addEventListener('keyup', handleEnterKeydown);
		}

		return () => {
			ref.current?.removeEventListener('keyup', handleEnterKeydown);
		};
	}, [ref.current]);

	return [ref];
};

export default useEnterToClick;
