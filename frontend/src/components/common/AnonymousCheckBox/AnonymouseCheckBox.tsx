import { ChangeEvent, Dispatch, SetStateAction } from 'react';

import * as S from '@/components/common/AnonymousCheckBox/AnonymousCheckBox.styles';

const AnonymouseCheckBox = ({
	setIsAnonymous,
}: {
	setIsAnonymous: Dispatch<SetStateAction<boolean>>;
}) => {
	const onChangeCheckBox = (e: ChangeEvent<HTMLInputElement>) => {
		setIsAnonymous(e.target.checked);
	};

	return (
		<S.AnonymousBox>
			<S.AnonymousCheckInput type="checkbox" onChange={onChangeCheckBox} />
			<p>익명</p>
		</S.AnonymousBox>
	);
};

export default AnonymouseCheckBox;
