import { useEffect, useRef, useState } from 'react';
import { useRecoilState } from 'recoil';

import SortDropdown from '@/components/@common/SortDropdown/SortDropDown';
import * as S from '@/components/search/SearchBar/SearchBar.styles';
import useSnackBar from '@/hooks/common/useSnackBar';
import { searchState } from '@/store/searchState';
import { validatedSearchInput } from '@/utils/validateInput';

const SearchBar = ({ isValid }: { isValid: boolean }) => {
	const [searchInput, setSearchInput] = useState('');
	const [searchIndex, setSearchIndex] = useState('게시물');
	const [searchInputState, setSearchInputState] = useRecoilState(searchState);
	const searchInputRef = useRef<HTMLInputElement>(null);

	const { showSnackBar } = useSnackBar();

	const handleChangeSearchInput = (e: { target: HTMLInputElement }) => {
		console.log(e.target.value);
		setSearchInput(e.target.value);
	};

	const handleSubmitSearchValue = (e: React.FormEvent) => {
		e.preventDefault();
		if (!validatedSearchInput(searchInput)) {
			showSnackBar('검색어는 2글자 이상 200글자 이하이여야 합니다');
			return;
		}
		setSearchInputState({
			isSearching: true,
			target: searchInput,
			isSearchOpen: searchInputState.isSearchOpen,
			searchIndex: searchIndex,
		});
	};

	useEffect(() => {
		if (searchInputState.isSearchOpen && searchInputRef.current !== null) {
			searchInputRef.current.focus();
		}
	}, [searchInputState]);

	return (
		<S.Container>
			{searchInputState.isSearchOpen && (
				<SortDropdown
					sortList={['유저', '게시물']}
					sortIndex={searchIndex}
					setSortIndex={setSearchIndex}
				/>
			)}
			<S.SearchBarBox
				isSearchOpen={searchInputState.isSearchOpen}
				onSubmit={handleSubmitSearchValue}
			>
				<S.SrOnlyLabel htmlFor="search-input">검색</S.SrOnlyLabel>
				<S.SearchInput
					type="text"
					readOnly={isValid}
					value={searchInput}
					ref={searchInputRef}
					onChange={handleChangeSearchInput}
					minLength={2}
					maxLength={200}
					id="search-input"
				/>
				<S.SearchButtonBox disabled={isValid} aria-label="검색하기">
					<S.SearchButton />
				</S.SearchButtonBox>
			</S.SearchBarBox>
		</S.Container>
	);
};

export default SearchBar;
