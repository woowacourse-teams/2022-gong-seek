import { ChangeEvent, Dispatch, SetStateAction } from 'react';

import * as S from '@/components/@common/AnonymousCheckBox/AnonymousCheckBox.styles';

const AnonymousCheckBox = ({
	setIsAnonymous,
}: {
	setIsAnonymous: Dispatch<SetStateAction<boolean>>;
}) => {
	const handleChangeAnonymouseCheckInput = (e: ChangeEvent<HTMLInputElement>) => {
		setIsAnonymous(e.target.checked);
	};

	return (
		<S.AnonymousBox>
			<S.AnonymousCheckInput type="checkbox" onChange={handleChangeAnonymouseCheckInput} />
			<S.AnonymousText>익명</S.AnonymousText>
		</S.AnonymousBox>
	);
};

export default AnonymousCheckBox;
