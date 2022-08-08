import { useState } from 'react';
import { useRecoilState } from 'recoil';

import * as S from '@/components/common/SearchBar/SearchBar.styles';
import useSnackBar from '@/hooks/useSnackBar';
import { searchState } from '@/store/searchState';
import { validatedSearchInput } from '@/utils/validateInput';

const SearchBar = ({ isValid }: { isValid: boolean }) => {
	const [searchInput, setSearchInput] = useState('');
	const [searchInputState, setSearchInputState] = useRecoilState(searchState);
	const { showSnackBar } = useSnackBar();

	const onChangeInputValue = (e: { target: HTMLInputElement }) => {
		setSearchInput(e.target.value);
	};

	const onSubmitSearchTarget = () => {
		if (!validatedSearchInput(searchInput)) {
			showSnackBar('검색어는 최소 2글자 이상이여야 합니다!');
			return;
		}
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
