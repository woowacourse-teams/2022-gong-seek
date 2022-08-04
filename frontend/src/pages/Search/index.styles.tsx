import styled from '@emotion/styled';

export const Container = styled.section`
    display: flex;

    width: 100vw;
    height: calc(100vh - 130px);
`;

export const EmptyResultBox = styled.div`
    display: flex;

    width: 100%;
    height: 100%;

    justify-content: center;
    align-items: center;

`

export const EmptyMessage = styled.div`
    font-size: ${({theme}) => theme.size.SIZE_016};
`
