import * as React from 'react';
import { styled } from '@mui/material/styles';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell, { tableCellClasses } from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import { Container } from '@mui/material';
import { useEffect } from 'react';
import { useState } from 'react';
import Pagination from '@mui/material/Pagination';
import Stack from '@mui/material/Stack';
import { Link } from 'react-router-dom';
import { setHeaders, getHeaders } from '../../commons/UserManager';
import { useContext } from 'react';

const StyledTableCell = styled(TableCell)(({ theme }) => ({
  [`&.${tableCellClasses.head}`]: {
    backgroundColor: theme.palette.common.black,
    color: theme.palette.common.white,
  },
  [`&.${tableCellClasses.body}`]: {
    fontSize: 14,
  },
}));

const StyledTableRow = styled(TableRow)(({ theme }) => ({
  '&:nth-of-type(odd)': {
    backgroundColor: theme.palette.action.hover,
  },
  // hide last border
  '&:last-child td, &:last-child th': {
    border: 0,
  },
}));

function createData(name, calories, fat, carbs, protein) {
  return { name, calories, fat, carbs, protein };
}

const rows = [
  createData('Frozen yoghurt', 159, 6.0, 24, 4.0),
  createData('Ice cream sandwich', 237, 9.0, 37, 4.3),
  createData('Eclair', 262, 16.0, 24, 6.0),
  createData('Cupcake', 305, 3.7, 67, 4.3),
  createData('Gingerbread', 356, 16.0, 49, 3.9),
];

export default function List() {

    //context
    const base_url = useContext(Base_URL)

    //글목록, pager
    const [list, setList] = useState({pager:"", list:[]});
    //페이지 번호 
    const [page, setPage] = useState(1);
    //검색어 
    const [search2, setSearch2] = useState("");
    //component 재랜더링 
    const [flag, setFlag] = useState(false);

    useEffect(()=>{
    
            let params = new URLSearchParams();
            params.append('page', page)
            params.append('search2',search2)

            //window는 생략 가능
            let accessToken = window.sessionStorage.getItem("AccessToken")
            let refreshToken = window.localStorage.getItem("RefreshToken")
            //accessToken이 만료될 수도 있으니까 refreshToken도 발급해서 이어서 사용하려고 함 
    
    
            fetch(`${base_url}/notices?${params}`,{
                headers: setHeaders
            })
            .then(r=>{
                

                getHeaders(r)

                return r.json()
            })
            .then(r=>{
                //console.log("서버응답:", r)
                setList(r)
                
            });
        },[flag]);//[page]);

    function pageClick(e){
        console.log(e.target.innerText);
        setPage(p)
        setFlag(flag)
    }



  return (
    <Container maxWidth="lg">
    <TableContainer component={Paper}>
      <Table sx={{ minWidth: 700 }} aria-label="customized table">
        <TableHead>
          <TableRow>
            <StyledTableCell>Num</StyledTableCell>
            <StyledTableCell align="right">Title</StyledTableCell>
            <StyledTableCell align="right">writer</StyledTableCell>
            <StyledTableCell align="right">Date</StyledTableCell>
            <StyledTableCell align="right">Hit</StyledTableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {list.list.map((row) => (
            <StyledTableRow key={row.boardNum}>
              <StyledTableCell component="th" scope="row">
                {row.boardNum}
              </StyledTableCell>
              <StyledTableCell align="right"><Link to={"/notice/detail"} state={{boardNum:row.boardNum}}>{row.boardTitle}</Link></StyledTableCell>
              <StyledTableCell align="right">{row.userName}</StyledTableCell>
              <StyledTableCell align="right">{row.boardDate}</StyledTableCell>
              <StyledTableCell align="right">{row.boardHit}</StyledTableCell>
            </StyledTableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>

    <Stack spacing={2}>
      <Pagination count={5} shape="rounded" onChange={pageClick}/>
    </Stack>

    </Container>
  );
}
