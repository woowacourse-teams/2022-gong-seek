import { useState } from 'react';
import { useRecoilState } from 'recoil';

import * as S from '@/components/common/SearchBar/SearchBar.styles';
import { searchState } from '@/store/searchState';

const SearchBar = ({ isValid }: { isValid: boolean }) => {
	const [searchInput, setSearchInput] = useState('');
	const [searchInputState, setSearchInputState] = useRecoilState(searchState);

	const onChangeInputValue = (e: { target: HTMLInputElement }) => {
		setSearchInput(e.target.value);
	};

	const onSubmitSearchTarget = () => {
		setSearchInputState({
			isSearching: true,
			target: searchInput,
			isSearchOpen: searchInputState.isSearchOpen,
		});
	};

	return (
		<S.Container>
			<S.SearchInput
				type="text"
				readOnly={isValid}
				value={searchInput}
				onChange={(e) => {
					onChangeInputValue(e);
				}}
			/>
			<S.SearchButtonBox disabled={isValid} onClick={onSubmitSearchTarget}>
				<S.SearchButton />
			</S.SearchButtonBox>
		</S.Container>
	);
};

export default SearchBar;
