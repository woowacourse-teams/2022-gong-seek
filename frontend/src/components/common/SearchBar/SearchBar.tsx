import { useState } from 'react';
import { useRecoilState } from 'recoil';

import * as S from '@/components/common/SearchBar/SearchBar.styles';
import SortDropdown from '@/components/common/SortDropdown/SortDropDown';
import useSnackBar from '@/hooks/useSnackBar';
import { searchState } from '@/store/searchState';
import { validatedSearchInput } from '@/utils/validateInput';

const SearchBar = ({ isValid }: { isValid: boolean }) => {
	const [searchInput, setSearchInput] = useState('');
	const [searchIndex, setSearchIndex] = useState('게시물');
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
			searchIndex: searchIndex,
		});
	};

	return (
		<S.Container>
			{searchInputState.isSearchOpen && (
				<SortDropdown
					sortList={['유저', '게시물']}
					sortIndex={searchIndex}
					setSortIndex={setSearchIndex}
				/>
			)}
			<S.SearchBarBox isSearchOpen={searchInputState.isSearchOpen}>
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
			</S.SearchBarBox>
		</S.Container>
	);
};

export default SearchBar;
