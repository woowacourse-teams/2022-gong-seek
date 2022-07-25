import { useState } from 'react';

import * as S from '@/components/common/SearchBar/SearchBar.styles';

const SearchBar = () => {
	const [search, setSearch] = useState('');
	return (
		<S.Container>
			<S.SearchInput type="text" />
			<S.SearchButton />
		</S.Container>
	);
};

export default SearchBar;
