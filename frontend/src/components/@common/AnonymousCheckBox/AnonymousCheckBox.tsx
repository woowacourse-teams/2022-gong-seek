import { ChangeEvent, Dispatch, SetStateAction } from 'react';

import * as S from '@/components/@common/AnonymousCheckBox/AnonymousCheckBox.styles';

const AnonymousCheckBox = ({
	setIsAnonymous,
}: {
	setIsAnonymous: Dispatch<SetStateAction<boolean>>;
}) => {
	const handleToggleAnoymouseCheck = (e: ChangeEvent<HTMLInputElement>) => {
		setIsAnonymous(e.target.checked);
	};

	return (
		<S.AnonymousBox>
			<S.AnonymousCheckInput type="checkbox" onChange={handleToggleAnoymouseCheck} />
			<S.AnonymousText>익명</S.AnonymousText>
		</S.AnonymousBox>
	);
};

export default AnonymousCheckBox;
