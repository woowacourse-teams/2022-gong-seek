import { useState } from 'react';

import * as S from '@/pages/HashTagSearch/HashTagSearchBox/HashTagSearchBox.styles';

const HashTagSearchBox = () => {
	const [isOpen, setIsOpen] = useState(false);

	return (
		<S.Container>
			<h2 hidden>해시태그들을 보여주는 곳입니다</h2>
		</S.Container>
	);
};

export default HashTagSearchBox;
