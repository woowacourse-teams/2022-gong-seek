import  { useState, useEffect } from 'react';
import * as S from '@/pages/Search/index.styles';
import { useRecoilState } from 'recoil';
import { searchState } from '@/store/searchState';
import { validatedSearchInput } from '@/utils/validateInput';
import SearchResult from './SearchResult/SearchResult';

const Search = () => {
  const [searchInputState, setSearchInputState] = useRecoilState(searchState);
  const [validSearch, setValidSearch] = useState(false);
  const {isSearchOpen, isSearching, target} = searchInputState;

  useEffect(() => {
    setSearchInputState({isSearchOpen: true, isSearching, target});

    return () => {
      setSearchInputState({isSearchOpen: false, isSearching: false, target: ''});
    }
  }, []);

  useEffect(() => {
    if(validatedSearchInput(target)){
      setValidSearch(true);
    }
  },[isSearching]);

  return (
    <S.Container>
      {
        validSearch ? <SearchResult target={target}/> : 
        <S.EmptyResultBox>
          <S.EmptyMessage>검색어를 입력해주세요</S.EmptyMessage>
        </S.EmptyResultBox>
      }
    </S.Container>
  )
}

export default Search